package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class SpotBugsWaiverTests {

  private static final Path FILTER = Path.of("config", "spotbugs-exclude.xml");

  @Test
  void waiversOnlyShrinkByIdentity() throws Exception {
    var prior = GitChangeSet.priorFile(FILTER);
    if (prior.isEmpty()) {
      return;
    }

    Set<Waiver> before = readWaivers(prior.orElseThrow());
    Set<Waiver> after = readWaivers(Files.readString(FILTER, StandardCharsets.UTF_8));
    Set<Waiver> added = new LinkedHashSet<>(after);
    added.removeAll(before);

    assertThat(added).as("new waiver identities").isEmpty();
  }

  @Test
  void filterIsStrictAndUnique() throws Exception {
    assertThat(readWaivers(Files.readString(FILTER, StandardCharsets.UTF_8)))
        .isNotEmpty()
        .hasSizeLessThanOrEqualTo(682);
  }

  @Test
  void rejectsDocumentTypes() {
    String document =
        "<!DOCTYPE FindBugsFilter [<!ENTITY sample SYSTEM \"file:///tmp/sample\">]>"
            + "<FindBugsFilter/>";

    assertThatThrownBy(() -> readWaivers(document)).isInstanceOf(SAXException.class);
  }

  @Test
  void waiversReferenceClasses() throws Exception {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    for (Waiver waiver : readWaivers(Files.readString(FILTER, StandardCharsets.UTF_8))) {
      assertThatCode(() -> Class.forName(waiver.className(), false, classLoader))
          .as(waiver.toString())
          .doesNotThrowAnyException();
    }
  }

  private static Set<Waiver> readWaivers(String xml) throws Exception {
    DocumentBuilderFactory factory = secureFactory();
    DocumentBuilder builder = factory.newDocumentBuilder();
    builder.setErrorHandler(new DefaultHandler());
    Document document = builder.parse(new InputSource(new StringReader(xml)));
    Element root = document.getDocumentElement();
    assertThat(root.getTagName()).isEqualTo("FindBugsFilter");

    Set<Waiver> waivers = new LinkedHashSet<>();
    for (Element match : directElements(root)) {
      assertThat(match.getTagName()).isEqualTo("Match");
      List<Element> parts = directElements(match);
      assertThat(parts).extracting(Element::getTagName).containsExactly("Bug", "Class");
      Waiver waiver =
          new Waiver(requiredAttr(parts.get(0), "pattern"), requiredAttr(parts.get(1), "name"));
      assertThat(waivers.add(waiver)).as("duplicate waiver %s", waiver).isTrue();
    }
    return Set.copyOf(waivers);
  }

  private static DocumentBuilderFactory secureFactory() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
    factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    factory.setXIncludeAware(false);
    factory.setExpandEntityReferences(false);
    return factory;
  }

  private static List<Element> directElements(Element parent) {
    List<Element> elements = new ArrayList<>();
    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (child instanceof Element element) {
        elements.add(element);
      }
    }
    return List.copyOf(elements);
  }

  private static String requiredAttr(Element element, String name) {
    assertThat(element.getAttributes().getLength()).as(element.getTagName()).isEqualTo(1);
    String value = element.getAttribute(name);
    assertThat(value).as(element.getTagName() + "." + name).isNotBlank();
    return value;
  }

  private record Waiver(String pattern, String className) {}
}
