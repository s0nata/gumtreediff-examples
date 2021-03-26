package io.github.s0nata;

import com.github.gumtreediff.actions.EditScript;
import com.github.gumtreediff.actions.EditScriptGenerator;
import com.github.gumtreediff.actions.SimplifiedChawatheScriptGenerator;
import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.gen.TreeGenerators;
import com.github.gumtreediff.gen.javaparser.JavaParserGenerator;
import com.github.gumtreediff.io.TreeIoUtils;
import com.github.gumtreediff.matchers.Mapping;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;

import java.io.IOException;
import java.util.Set;


/**
 * to run the automatically created JAR from the command line do:
 *
 * @code{$ java -cp gumtreediff-api-examples-maven-1.0-SNAPSHOT.jar io.github.s0nata.ExamplesDemo}
 *
 *
 * see also @link{https://stackoverflow.com/questions/9689793/cant-execute-jar-file-no-main-manifest-attribute}
 */
public class ExamplesDemo {

  private static String srcFilePath = "resources/SampleFile0.java";
  private static String dstFilePath = "resources/SampleFile1.java";

  private static void initGTD () {
    Run.initClients();
    Run.initGenerators(); // registers the available parsers

    // in case you want to see the basic help message:
    // System.out.println("GumTreeDiff help message:");
    // Run.listCommand(System.out);

  }

  public static void main(String[] args) {

    // all examples reproduced according to official documentation, see version 14:
    // https://github.com/GumTreeDiff/gumtree/wiki/GumTree-API/e6843abef6f3162c2fa2ee9db054bf93c0c891e4

    initGTD();

    System.out.println("\nEXAMPLE 1: Parsing a file:\n");
    exampleParsing(srcFilePath);
    System.out.println("\nEXAMPLE 2: Getting the mappings between two trees:\n");
    exampleMapping(srcFilePath, dstFilePath);
    System.out.println("\nEXAMPLE 3: Computing the actions between two trees:\n");
    exampleActions(srcFilePath, dstFilePath);
  }

  private static void exampleParsing(String filePath) {
    try {
      // FIRST, get the parse tree and its root
      // OPTION 1: automatic parsing using generator registry
      // -- retrieves and applies the default parser for the file
      TreeContext tc1 = TreeGenerators.getInstance().getTree(filePath);
      // -- retrieves the root of the tree
      Tree t1 = tc1.getRoot();
      // OPTION 2: parsing using a specific generator
      // -- instantiates and applies the JavaParser generator
      TreeContext tc2 = new JavaParserGenerator().generateFrom().file(filePath);
      Tree t2 = tc2.getRoot(); // retrieves the root of the tree
      // OPTION 2+: can be a one-liner
      Tree t3 = new JavaParserGenerator().generateFrom().file(filePath).getRoot();

      // SECOND, display the tree
      Tree curTree = t1;
      TreeContext curContext = tc1;
      // -- displays the tree in our ad-hoc format
      System.out.println(curTree.toTreeString());
      // or in some other syntax (from respective the TreeContext)
      // -- displays the tree in LISP syntax
      System.out.println(TreeIoUtils.toLisp(curContext).toString());
      //      System.out.println(TreeIoUtils.toJson(curContext).toString());
      //      System.out.println(TreeIoUtils.toCompactXml(curContext).toString());
      //      System.out.println(TreeIoUtils.toXml(curContext).toString());
    } catch (IOException treeGetException) {
      System.out.println(treeGetException.getMessage());
    }
  }

  private static void exampleMapping(String srcFile, String dstFile) {
    // input data
    Tree src;
    Tree dst;
    try {
      // parse AST trees
      // -- retrieves and applies the default parser for the file
      src = TreeGenerators.getInstance().getTree(srcFile).getRoot();
      dst = TreeGenerators.getInstance().getTree(dstFile).getRoot();

      // -- retrieves the default matcher
      Matcher defaultMatcher = Matchers.getInstance().getMatcher();
      // -- computes the mappings between the trees
      MappingStore mappings = defaultMatcher.match(src, dst);

      // NS: this goes beyond the example, I am just trying to get any human-readable output
      Set<Mapping> mappingSet = mappings.asSet();
      for (Mapping mapping : mappingSet) {
        System.out.println(mapping.toString());
      }
    } catch (IOException treeGenerationException) {
      System.out.println(treeGenerationException.getMessage());
    }
  }

  private static void exampleActions(String srcFile, String dstFile) {
    // input data
    Tree src;
    Tree dst;
    try {
      // parse AST trees
      // -- retrieves and applies the default parser for the file
      src = TreeGenerators.getInstance().getTree(srcFile).getRoot();
      dst = TreeGenerators.getInstance().getTree(dstFile).getRoot();

      // -- retrieves the default matcher
      Matcher defaultMatcher = Matchers.getInstance().getMatcher();
      // -- computes the mappings between the trees
      MappingStore mappings = defaultMatcher.match(src, dst);

      // -- instantiates the simplified Chawathe script generator
      EditScriptGenerator eSG = new SimplifiedChawatheScriptGenerator();
      // -- computes the edit script
      EditScript actions = eSG.computeActions(mappings);

      // NS: this goes beyond the example, I am just trying to get any human-readable output
      for (Action a : actions) {
        System.out.println(a.toString());
      }

    } catch (IOException treeGenerationException) {
      System.out.println(treeGenerationException.getMessage());
    }
  }

}
