/*
 * FileTypeRegistry.java
 *
 * Copyright (C) 2022 by Posit Software, PBC
 *
 * Unless you have received this program directly from Posit Software pursuant
 * to the terms of a commercial license agreement with Posit Software, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.studio.client.common.filetypes;

import java.util.HashMap;

import org.rstudio.core.client.FilePosition;
import org.rstudio.core.client.StringUtil;
import org.rstudio.core.client.files.FileSystemItem;
import org.rstudio.core.client.resources.ImageResource2x;
import org.rstudio.studio.client.application.events.EventBus;
import org.rstudio.studio.client.common.GlobalDisplay;
import org.rstudio.studio.client.common.StudioClientCommonConstants;
import org.rstudio.studio.client.common.filetypes.TextFileType.WordWrap;
import org.rstudio.studio.client.common.filetypes.model.NavigationMethods;
import org.rstudio.studio.client.common.reditor.EditorLanguage;
import org.rstudio.studio.client.common.satellite.Satellite;
import org.rstudio.studio.client.server.ServerError;
import org.rstudio.studio.client.server.ServerRequestCallback;
import org.rstudio.studio.client.workbench.model.Session;
import org.rstudio.studio.client.workbench.prefs.model.UserPrefs;
import org.rstudio.studio.client.workbench.views.files.model.FilesServerOperations;
import org.rstudio.studio.client.workbench.views.source.SourceSatellite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FileTypeRegistry
{
   private static final StudioClientCommonConstants constants_ = GWT.create(StudioClientCommonConstants.class);
   private static final FileIconResources ICONS = FileIconResources.INSTANCE;

   public static final TextFileType TEXT =
         new TextFileType("text", FileIcon.TEXT_ICON.getDescription(), EditorLanguage.LANG_PLAIN, "",
                          FileIcon.TEXT_ICON.getImageResource(),
                          WordWrap.DEFAULT,
                          false, false, false, false, false, false, false, false, false, true, false, false);

   public static final TextFileType R =
         new RFileType("r_source", constants_.rScriptLabel(), EditorLanguage.LANG_R, ".R",
                       new ImageResource2x(ICONS.iconRdoc2x()));

   public static final TextFileType RD =
      new TextFileType("r_doc", constants_.rdFile(), EditorLanguage.LANG_RDOC, ".Rd",
                       new ImageResource2x(ICONS.iconRd2x()),
                       WordWrap.DEFAULT, // word-wrap
                       true, // source on save aka preview on save
                       false, false, false,
                       true, // preview html
                       false, false, false, false,
                       true, // check spelling
                       false,
                       false);

   public static final TextFileType DCF =
         new TextFileType("dcf", "DCF", EditorLanguage.LANG_DCF, ".dcf", new ImageResource2x(ICONS.iconDCF2x()),
                          WordWrap.DEFAULT, false, false, false, false,
                          false, false, false, false, false, false, false, false);

   public static final TextFileType INI =
         new TextFileType("ini", "INI", EditorLanguage.LANG_INI, ".ini", new ImageResource2x(ICONS.iconDCF2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);


   public static final TextFileType STAN = new StanFileType();

   public static final TextFileType MERMAID = new MermaidFileType();
   public static final TextFileType GRAPHVIZ = new GraphvizFileType();


   public static final TextFileType NAMESPACE =
     new TextFileType("r_namespace", constants_.namespaceLabel(), EditorLanguage.LANG_R, "",
                      new ImageResource2x(ICONS.iconText2x()), WordWrap.DEFAULT, false, false, false, false,
                      false, false, false, false, false, false, false, false);

   public static final TextFileType SWEAVE =
      new SweaveFileType("sweave", "R Sweave",
          EditorLanguage.LANG_SWEAVE, ".Rnw",new ImageResource2x(ICONS.iconRsweave2x()));

   public static final TexFileType TEX =
         new TexFileType("tex", "TeX", EditorLanguage.LANG_TEX, ".tex",
                          new ImageResource2x(ICONS.iconTex2x()));

   public static final PlainTextFileType RHISTORY =
      new PlainTextFileType("r_history", constants_.rHistoryLabel(), ".Rhistory",
                            new ImageResource2x(ICONS.iconRhistory2x()));

   public static final RWebContentFileType RMARKDOWN =
         new RWebContentFileType("r_markdown", constants_.rMarkdownLabel(), EditorLanguage.LANG_RMARKDOWN,
                              ".Rmd", new ImageResource2x(ICONS.iconRmarkdown2x()), true, true, true);

   public static final RWebContentFileType RNOTEBOOK =
         new RWebContentFileType("r_notebook", constants_.rNotebookLabel(), EditorLanguage.LANG_RMARKDOWN,
                                 ".nb.html", new ImageResource2x(ICONS.iconRnotebook2x()), true);
   
   public static final QuartoFileType QUARTO = new QuartoFileType();

   public static final RWebContentFileType RPRESENTATION = new RPresentationFileType();

   public static final WebContentFileType MARKDOWN =
      new WebContentFileType("markdown", constants_.markdownLabel(), EditorLanguage.LANG_MARKDOWN,
                           ".md", new ImageResource2x(ICONS.iconMarkdown2x()), true, true);


   public static final RWebContentFileType RHTML =
         new RWebContentFileType("r_html", "R HTML", EditorLanguage.LANG_RHTML,
                              ".Rhtml", new ImageResource2x(ICONS.iconRhtml2x()), false);

   public static final WebContentFileType HTML =
         new WebContentFileType("html", "HTML", EditorLanguage.LANG_HTML,
                              ".html", new ImageResource2x(ICONS.iconHTML2x()), false, true);

   public static final TextFileType CSS =
         new TextFileType("css", "CSS", EditorLanguage.LANG_CSS, ".css",
                          new ImageResource2x(ICONS.iconCss2x()),
                          WordWrap.DEFAULT,
                          false, false, false, false, false, false, false, false, false, false, false, false);

   public static final TextFileType SCSS =
         new TextFileType("scss", "SCSS", EditorLanguage.LANG_SCSS, ".scss",
                          new ImageResource2x(ICONS.iconScss2x()),
                          WordWrap.DEFAULT,
                          false, false, false, false, false, false, false, false, false, false, false, false);

   public static final TextFileType SASS =
         new TextFileType("sass", "SASS", EditorLanguage.LANG_SASS, ".sass",
                          new ImageResource2x(ICONS.iconScss2x()),
                          WordWrap.DEFAULT,
                          false, false, false, false, false, false, false, false, false, false, false, false);
   
   public static final TextFileType LESS =
         new TextFileType("less", "LESS", EditorLanguage.LANG_LESS, ".less",
                          new ImageResource2x(ICONS.iconLess2x()),
                          WordWrap.DEFAULT,
                          false, false, false, false, false, false, false, false, false, false, false, false);
                 
   public static final TextFileType JS =
         new TextFileType("js", "JavaScript", EditorLanguage.LANG_JAVASCRIPT, ".js",
                          new ImageResource2x(ICONS.iconJavascript2x()),
                          WordWrap.DEFAULT,
                          true, false, false, false, false, false, false, false, false, false, false, false);

   public static final TextFileType JSON =
         new TextFileType("json", "JSON", EditorLanguage.LANG_JAVASCRIPT, ".json",
                          new ImageResource2x(ICONS.iconJavascript2x()),
                          WordWrap.DEFAULT,
                          false, false, false, false, false, false, false, false, false, false, false, false);


   public static final TextFileType PYTHON =
         new PythonFileType("python", "Python", EditorLanguage.LANG_PYTHON, ".py", new ImageResource2x(ICONS.iconPython2x()));

   public static final TextFileType SQL =
         new TextFileType("sql", "SQL", EditorLanguage.LANG_SQL, ".sql",
                          new ImageResource2x(ICONS.iconSql2x()),
                          WordWrap.DEFAULT, true, false, false, false,
                          false, false, false, false, false, false, false, false);

   public static final TextFileType SH = new ScriptFileType(
         "sh", "Shell", EditorLanguage.LANG_SH, ".sh", new ImageResource2x(ICONS.iconSh2x()),
         null, true, false);

   public static final TextFileType TOML =
         new TextFileType("toml", "TOML", EditorLanguage.LANG_TOML, ".toml",
                          new ImageResource2x(ICONS.iconToml2x()),
                          WordWrap.DEFAULT, false, false, false, false,
                          false, false, false, false, false, false, false, false);

   public static final TextFileType YAML =
         new TextFileType("yaml", "YAML", EditorLanguage.LANG_YAML, ".yml",
                          new ImageResource2x(ICONS.iconYaml2x()),
                          WordWrap.DEFAULT, false, false, false, false,
                          false, false, false, false, false, false, false, false);

   public static final TextFileType XML =
         new TextFileType("xml", "XML", EditorLanguage.LANG_XML, ".xml",
                          new ImageResource2x(ICONS.iconXml2x()),
                          WordWrap.DEFAULT, false, false, false, false,
                          false, false, false, false, false, false, false, false);

   public static final TextFileType H = new CppFileType("h", ".h", new ImageResource2x(ICONS.iconH2x()), true, false);
   public static final TextFileType C = new CppFileType("c", ".c", new ImageResource2x(ICONS.iconC2x()), false, false);
   public static final TextFileType HPP = new CppFileType("hpp", ".hpp", new ImageResource2x(ICONS.iconHpp2x()), true, false);
   public static final TextFileType CPP = new CppFileType("cpp", ".cpp", new ImageResource2x(ICONS.iconCpp2x()), true, true);

   public static final TextFileType CLOJURE =
         new TextFileType("clojure", "Clojure", EditorLanguage.LANG_CLOJURE, ".clj", new ImageResource2x(ICONS.iconClojure2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType COFFEE =
         new TextFileType("coffee", "Coffee", EditorLanguage.LANG_COFFEE, ".coffee", new ImageResource2x(ICONS.iconCoffee2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType CSHARP =
         new TextFileType("csharp", "C#", EditorLanguage.LANG_CSHARP, ".cs", new ImageResource2x(ICONS.iconCsharp2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);


   public static final TextFileType DOCKERFILE =
         new TextFileType("dockerfile", "Dockerfile", EditorLanguage.LANG_DOCKERFILE, "Dockerfile", new ImageResource2x(ICONS.iconDockerfile2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);
 
   public static final TextFileType FORTRAN =
         new TextFileType("fortran", "Fortran", EditorLanguage.LANG_FORTRAN, ".f", new ImageResource2x(ICONS.iconFortran2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);
   
   public static final TextFileType GITIGNORE =
         new TextFileType("gitignore", "Gitignore", EditorLanguage.LANG_GITIGNORE, ".gitignore", new ImageResource2x(ICONS.iconGitignore2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType GO =
         new TextFileType("go", "Go", EditorLanguage.LANG_GO, ".go", new ImageResource2x(ICONS.iconGo2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType GROOVY =
         new TextFileType("groovy", "Groovy", EditorLanguage.LANG_GROOVY, ".groovy", new ImageResource2x(ICONS.iconGroovy2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType HASKELL =
         new TextFileType("haskell", "Haskell", EditorLanguage.LANG_HASKELL, ".haskell", new ImageResource2x(ICONS.iconHaskell2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType HAXE =
         new TextFileType("haxe", "Haxe", EditorLanguage.LANG_HAXE, ".haxe", new ImageResource2x(ICONS.iconHaxe2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType JAVA =
         new TextFileType("java", "Java", EditorLanguage.LANG_JAVA, ".java", new ImageResource2x(ICONS.iconJava2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType JULIA =
         new TextFileType("julia", "Julia", EditorLanguage.LANG_JULIA, ".jl", new ImageResource2x(ICONS.iconJulia2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType LISP =
         new TextFileType("lisp", "Lisp", EditorLanguage.LANG_LISP, ".lisp", new ImageResource2x(ICONS.iconLisp2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType LUA =
         new TextFileType("lua", "Lua", EditorLanguage.LANG_LUA, ".lua", new ImageResource2x(ICONS.iconLua2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType MAKEFILE =
         new TextFileType("makefile", "Makefile", EditorLanguage.LANG_MAKEFILE, ".makefile", new ImageResource2x(ICONS.iconMakefile2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType MATLAB =
         new TextFileType("matlab", "Matlab", EditorLanguage.LANG_MATLAB, ".m", new ImageResource2x(ICONS.iconMatlab2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType PERL =
         new TextFileType("perl", "Perl", EditorLanguage.LANG_PERL, ".pl", new ImageResource2x(ICONS.iconPerl2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType RUBY =
         new TextFileType("ruby", "Ruby", EditorLanguage.LANG_RUBY, ".rb", new ImageResource2x(ICONS.iconRuby2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType RUST =
         new TextFileType("rust", "Rust", EditorLanguage.LANG_RUST, ".rs", new ImageResource2x(ICONS.iconRust2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType SCALA =
         new TextFileType("scala", "Scala", EditorLanguage.LANG_SCALA, ".scala", new ImageResource2x(ICONS.iconScala2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final TextFileType SNIPPETS =
         new TextFileType("snippets", "Snippets", EditorLanguage.LANG_SNIPPETS, ".snippets", new ImageResource2x(ICONS.iconSnippets2x()),
               WordWrap.DEFAULT, false, false, false, false,
               false, false, false, false, false, false, false, false);

   public static final RDataType RDATA = new RDataType();
   public static final RDSDataType RDS = new RDSDataType();
   public static final RProjectType RPROJECT = new RProjectType();

   public static final DataFrameType DATAFRAME = new DataFrameType();
   public static final UrlContentType URLCONTENT = new UrlContentType();
   public static final CodeBrowserType CODEBROWSER = new CodeBrowserType();
   public static final ProfilerType PROFILER = new ProfilerType();
   public static final ObjectExplorerFileType OBJECT_EXPLORER = new ObjectExplorerFileType();

   public static final BrowserType BROWSER = new BrowserType();

   @Inject
   public FileTypeRegistry(EventBus eventBus,
                           Satellite satellite,
                           Session session,
                           UserPrefs userPrefs,
                           GlobalDisplay globalDisplay,
                           FilesServerOperations server)
   {
      eventBus_ = eventBus;
      satellite_ = satellite;
      server_ = server;
      session_ = session;
      globalDisplay_ = globalDisplay;

      if (!Satellite.isCurrentWindowSatellite())
         exportEditFileCallback();

      FileIconResources icons = ICONS;

      register("", TEXT, new ImageResource2x(icons.iconText2x()));
      register(".Rbuildignore", TEXT, new ImageResource2x(icons.iconText2x()));
      register(".Renviron", SH, new ImageResource2x(icons.iconSh2x()));
      register(".Rprofile", R, new ImageResource2x(icons.iconRprofile2x()));
      register(".emacs", LISP, new ImageResource2x(icons.iconLisp2x()));
      register(".env", SH, new ImageResource2x(icons.iconSh2x()));
      register(".gitignore", GITIGNORE, new ImageResource2x(icons.iconGitignore2x()));
      register(".lintr", TEXT, new ImageResource2x(icons.iconText2x()));
      register("BUGS", TEXT, new ImageResource2x(icons.iconText2x()));
      register("CHANGELOG", TEXT, new ImageResource2x(icons.iconText2x()));
      register("CHANGES", TEXT, new ImageResource2x(icons.iconText2x()));
      register("CITATION", TEXT, new ImageResource2x(icons.iconText2x()));
      register("COPYING", TEXT, new ImageResource2x(icons.iconText2x()));
      register("COPYING.LIB", TEXT, new ImageResource2x(icons.iconText2x()));
      register("DESCRIPTION", DCF, new ImageResource2x(icons.iconDCF2x()));
      register("Dockerfile", DOCKERFILE, new ImageResource2x(icons.iconDockerfile2x()));
      register("GNUmakefile", MAKEFILE, new ImageResource2x(icons.iconMakefile2x()));
      register("INDEX", TEXT, new ImageResource2x(icons.iconText2x()));
      register("INSTALL", SH, new ImageResource2x(icons.iconSh2x()));
      register("Jenkinsfile", GROOVY, new ImageResource2x(icons.iconGroovy2x()));
      register("LICENCE", TEXT, new ImageResource2x(icons.iconText2x()));
      register("LICENSE", TEXT, new ImageResource2x(icons.iconText2x()));
      register("MD5", TEXT, new ImageResource2x(icons.iconText2x()));
      register("Makefile", MAKEFILE, new ImageResource2x(icons.iconMakefile2x()));
      register("Makefile.in", MAKEFILE, new ImageResource2x(icons.iconMakefile2x()));
      register("Makefile.win", MAKEFILE, new ImageResource2x(icons.iconMakefile2x()));
      register("Makevars", MAKEFILE, new ImageResource2x(icons.iconMakefile2x()));
      register("Makevars.in", MAKEFILE, new ImageResource2x(icons.iconMakefile2x()));
      register("Makevars.win", MAKEFILE, new ImageResource2x(icons.iconMakefile2x()));
      register("NAMESPACE", NAMESPACE, new ImageResource2x(icons.iconText2x()));
      register("NEWS", TEXT, new ImageResource2x(icons.iconText2x()));
      register("PORTING", TEXT, new ImageResource2x(icons.iconText2x()));
      register("README", TEXT, new ImageResource2x(icons.iconText2x()));
      register("Renviron.site", SH, new ImageResource2x(icons.iconSh2x()));
      register("Rprofile.site", R, new ImageResource2x(icons.iconRprofile2x()));
      register("Snakefile", PYTHON, new ImageResource2x(icons.iconPython2x()));
      register("THANKS", TEXT, new ImageResource2x(icons.iconText2x()));
      register("TODO", TEXT, new ImageResource2x(icons.iconText2x()));
      register("TUTORIAL", DCF, new ImageResource2x(icons.iconDCF2x()));
      register("cleanup", SH, new ImageResource2x(icons.iconSh2x()));
      register("cleanup.win", SH, new ImageResource2x(icons.iconSh2x()));
      register("configure", SH, new ImageResource2x(icons.iconSh2x()));
      register("configure.win", SH, new ImageResource2x(icons.iconSh2x()));
      register("makefile", MAKEFILE, new ImageResource2x(icons.iconMakefile2x()));
      register("packrat.lock", DCF, new ImageResource2x(icons.iconDCF2x()));
      register("renv.lock", JSON, new ImageResource2x(icons.iconDCF2x()));
      

      register("*.Rout.save", TEXT, new ImageResource2x(icons.iconText2x()));
      register("*.Rprofvis", PROFILER, new ImageResource2x(icons.iconRprofile2x()));
      register("*.Rproj", RPROJECT, new ImageResource2x(icons.iconRproject2x()));
      register("*.bbl", TEX, new ImageResource2x(icons.iconTex2x()));
      register("*.bib", TEXT, new ImageResource2x(icons.iconText2x()));
      register("*.c", C, new ImageResource2x(icons.iconC2x()));
      register("*.cc", CPP, new ImageResource2x(icons.iconCpp2x()));
      register("*.clj", CLOJURE, new ImageResource2x(icons.iconClojure2x()));
      register("*.cloj", CLOJURE, new ImageResource2x(icons.iconClojure2x()));
      register("*.clojure", CLOJURE, new ImageResource2x(icons.iconClojure2x()));
      register("*.cls", TEX, new ImageResource2x(icons.iconTex2x()));
      register("*.coffee", COFFEE, new ImageResource2x(icons.iconCoffee2x()));
      register("*.cpp", CPP, new ImageResource2x(icons.iconCpp2x()));
      register("*.cs", CSHARP, new ImageResource2x(icons.iconCsharp2x()));
      register("*.css", CSS, new ImageResource2x(icons.iconCss2x()));
      register("*.dcf", DCF, new ImageResource2x(icons.iconDCF2x()));
      register("*.dot", GRAPHVIZ, new ImageResource2x(icons.iconGraphviz2x()));
      register("*.dtx", TEX, new ImageResource2x(icons.iconTex2x()));
      register("*.el", LISP, new ImageResource2x(icons.iconLisp2x()));
      register("*.f", FORTRAN, new ImageResource2x(icons.iconFortran2x()));
      register("*.f", TEXT, new ImageResource2x(icons.iconText2x()));
      register("*.f03", FORTRAN, new ImageResource2x(icons.iconFortran2x()));
      register("*.f90", FORTRAN, new ImageResource2x(icons.iconFortran2x()));
      register("*.f95", FORTRAN, new ImageResource2x(icons.iconFortran2x()));
      register("*.go", GO, new ImageResource2x(icons.iconGo2x()));
      register("*.groovy", GROOVY, new ImageResource2x(icons.iconGroovy2x()));
      register("*.gv", GRAPHVIZ, new ImageResource2x(icons.iconGraphviz2x()));
      register("*.h", H, new ImageResource2x(icons.iconH2x()));
      register("*.haskell", HASKELL, new ImageResource2x(icons.iconHaskell2x()));
      register("*.haxe", HAXE, new ImageResource2x(icons.iconHaxe2x()));
      register("*.hh", HPP, new ImageResource2x(icons.iconHpp2x()));
      register("*.hpp", HPP, new ImageResource2x(icons.iconHpp2x()));
      register("*.htm", HTML, new ImageResource2x(icons.iconHTML2x()));
      register("*.html", HTML, new ImageResource2x(icons.iconHTML2x()));
      register("*.ini", INI, new ImageResource2x(icons.iconDCF2x()));
      register("*.ins", TEX, new ImageResource2x(icons.iconTex2x()));
      register("*.java", JAVA, new ImageResource2x(icons.iconJava2x()));
      register("*.jl", JULIA, new ImageResource2x(icons.iconJulia2x()));
      register("*.js", JS, new ImageResource2x(icons.iconJavascript2x()));
      register("*.json", JSON, new ImageResource2x(icons.iconJavascript2x()));
      register("*.julia", JULIA, new ImageResource2x(icons.iconJulia2x()));
      register("*.latex", TEX, new ImageResource2x(icons.iconTex2x()));
      register("*.less", LESS, new ImageResource2x(icons.iconLess2x()));
      register("*.lisp", LISP, new ImageResource2x(icons.iconLisp2x()));
      register("*.log", TEXT, new ImageResource2x(icons.iconText2x()));
      register("*.lua", LUA, new ImageResource2x(icons.iconLua2x()));
      register("*.m", MATLAB, new ImageResource2x(icons.iconMatlab2x()));
      register("*.make", MAKEFILE, new ImageResource2x(icons.iconMakefile2x()));
      register("*.markdown*", MARKDOWN, new ImageResource2x(icons.iconMarkdown2x()));
      register("*.md", MARKDOWN, new ImageResource2x(icons.iconMarkdown2x()));
      register("*.mdtxt", MARKDOWN, new ImageResource2x(icons.iconMarkdown2x()));
      register("*.mmd", MERMAID, new ImageResource2x(icons.iconMermaid2x()));
      register("*.nb.html", RNOTEBOOK, new ImageResource2x(icons.iconRnotebook2x()));
      register("*.nf", GROOVY, new ImageResource2x(icons.iconGroovy2x()));
      register("*.nw", SWEAVE, new ImageResource2x(icons.iconRsweave2x()));
      register("*.ojs", JS, new ImageResource2x(icons.iconJavascript2x()));
      register("*.pl", PERL, new ImageResource2x(icons.iconPerl2x()));
      register("*.py", PYTHON, new ImageResource2x(icons.iconPython2x()));
      register("*.q", R, new ImageResource2x(icons.iconRdoc2x()));
      register("*.qmd", QUARTO, new ImageResource2x(icons.iconQuarto2x()));
      register("*.r", R, new ImageResource2x(icons.iconRdoc2x()));
      register("*.rb", RUBY, new ImageResource2x(icons.iconRuby2x()));
      register("*.rd", RD, new ImageResource2x(icons.iconRd2x()));
      register("*.rda", RDATA, new ImageResource2x(icons.iconRdata2x()));
      register("*.rdata", RDATA, new ImageResource2x(icons.iconRdata2x()));
      register("*.rds", RDS, new ImageResource2x(icons.iconRdata2x()));
      register("*.rhistory", RHISTORY, new ImageResource2x(icons.iconRhistory2x()));
      register("*.rhtml", RHTML, new ImageResource2x(icons.iconRhtml2x()));
      register("*.rmarkdown", RMARKDOWN, new ImageResource2x(icons.iconRmarkdown2x()));
      register("*.rmd", RMARKDOWN, new ImageResource2x(icons.iconRmarkdown2x()));
      register("*.rnw", SWEAVE, new ImageResource2x(icons.iconRsweave2x()));
      register("*.rpres", RPRESENTATION, new ImageResource2x(icons.iconRpresentation2x()));
      register("*.rproj", RPROJECT, new ImageResource2x(icons.iconRproject2x()));
      register("*.rs", RUST, new ImageResource2x(icons.iconRust2x()));
      register("*.rtex", SWEAVE, new ImageResource2x(icons.iconRsweave2x()));
      register("*.s", R, new ImageResource2x(icons.iconRdoc2x()));
      register("*.sass", SASS, new ImageResource2x(icons.iconScss2x()));
      register("*.scala", SCALA, new ImageResource2x(icons.iconScala2x()));
      register("*.scss", SCSS, new ImageResource2x(icons.iconScss2x()));
      register("*.sh", SH, new ImageResource2x(icons.iconSh2x()));
      register("*.smk", PYTHON, new ImageResource2x(icons.iconPython2x()));
      register("*.snippets", SNIPPETS, new ImageResource2x(icons.iconSnippets2x()));
      register("*.snw", SWEAVE, new ImageResource2x(icons.iconRsweave2x()));
      register("*.sql", SQL, new ImageResource2x(icons.iconSql2x()));
      register("*.stan", STAN, new ImageResource2x(icons.iconStan2x()));
      register("*.sty", TEX, new ImageResource2x(icons.iconTex2x()));
      register("*.tex", TEX, new ImageResource2x(icons.iconTex2x()));
      register("*.tml", TOML, new ImageResource2x(icons.iconToml2x()));
      register("*.toml", TOML, new ImageResource2x(icons.iconToml2x()));
      register("*.ts", JS, new ImageResource2x(icons.iconJavascript2x()));
      register("*.txt", TEXT, new ImageResource2x(icons.iconText2x()));
      register("*.xml", XML, new ImageResource2x(icons.iconXml2x()));
      register("*.yaml", YAML, new ImageResource2x(icons.iconYaml2x()));
      register("*.yml", YAML, new ImageResource2x(icons.iconYaml2x()));

      
      registerIcon(".jpg", new FileIcon(new ImageResource2x(icons.iconPng2x()), "JPG"));
      registerIcon(".jpeg", new FileIcon(new ImageResource2x(icons.iconPng2x()), "JPEG"));
      registerIcon(".gif", new FileIcon(new ImageResource2x(icons.iconPng2x()), "GIF"));
      registerIcon(".bmp", new FileIcon(new ImageResource2x(icons.iconPng2x()), "BMP"));
      registerIcon(".tiff", new FileIcon(new ImageResource2x(icons.iconPng2x()), "TIFF"));
      registerIcon(".tif", new FileIcon(new ImageResource2x(icons.iconPng2x()), "TIF"));
      registerIcon(".png", new FileIcon(new ImageResource2x(icons.iconPng2x()), "PNG"));
      registerIcon(".webp", new FileIcon(new ImageResource2x(icons.iconPng2x()), "WEBP"));

      registerIcon(".pdf", FileIcon.PDF_ICON);
      registerIcon(".csv", FileIcon.CSV_ICON);
      registerIcon(".docx", new FileIcon(new ImageResource2x(icons.iconWord2x()), "DOCX"));
      registerIcon(".pptx", new FileIcon(new ImageResource2x(icons.iconPowerpoint2x()), "PPTX"));

      for (FileType fileType : FileType.ALL_FILE_TYPES)
      {
         assert !fileTypesByTypeName_.containsKey(fileType.getTypeId());
         fileTypesByTypeName_.put(fileType.getTypeId(), fileType);
      }
   }

   public void openFile(FileSystemItem file)
   {
      openFile(file, true);
   }

   public void openFile(final FileSystemItem file, final boolean canUseBrowser)
   {
      FileType fileType = getTypeForFile(file);
      if (fileType != null)
      {
         fileType.openFile(file, eventBus_);
      }
      else
      {
         // build default command to use if we have an error or the
         // file is not a text file
         final Command defaultCommand = new Command() {
            @Override
            public void execute()
            {
               if (canUseBrowser)
               {
                  if (session_.getSessionInfo().getAllowFileDownloads())
                  {
                     BROWSER.openFile(file, eventBus_);
                  }
                  else
                  {
                     globalDisplay_.showErrorMessage(
                     constants_.fileDownloadErrorCaption(),
                     constants_.fileDownloadErrorMessage());
                  }
               }
            }
         };

         // check with the server to see if this is likely to be a text file
         server_.isTextFile(file.getPath(),
                            new ServerRequestCallback<Boolean>() {
            @Override
            public void onResponseReceived(Boolean isText)
            {
               if (isText)
                  TEXT.openFile(file, eventBus_);
               else
                  defaultCommand.execute();
            }

            @Override
            public void onError(ServerError error)
            {
               defaultCommand.execute();
            }
         });
      }
   }

   public void editFile(FileSystemItem file)
   {
      editFile(file, null);
   }

   public void editFile(FileSystemItem file, FilePosition position)
   {
      editFile(file, position, false);
   }

   public void editFile(FileSystemItem file,
                        FilePosition position,
                        boolean highlightLine)
   {
      // edit the file in the main window unless this is a source satellite
      // (in which case we want to edit it locally)
      if (Satellite.isCurrentWindowSatellite() &&
          !satellite_.getSatelliteName().startsWith(SourceSatellite.NAME_PREFIX))
      {
         satellite_.focusMainWindow();
         callSatelliteEditFile(file.cast(), position.cast(), highlightLine);
      }
      else
      {
         FileType fileType = getTypeForFile(file);
         if (fileType != null
            && !(fileType instanceof TextFileType)
            && !(fileType instanceof ProfilerType))
         {
            fileType = TEXT;
         }

         if (fileType != null)
            fileType.openFile(file,
                  position,
                  highlightLine ?
                        NavigationMethods.HIGHLIGHT_LINE :
                        NavigationMethods.DEFAULT,
                  eventBus_);
      }
   }

   private void satelliteEditFile(JavaScriptObject file,
                                  JavaScriptObject position,
                                  boolean highlightLine)
   {
      FileSystemItem fsi = file.cast();
      FilePosition pos = position.cast();
      editFile(fsi, pos, highlightLine);
   }

   private final native void exportEditFileCallback()/*-{
      var registry = this;
      $wnd.editFileFromRStudioSatellite = $entry(
         function(file, position, highlightLine) {
            registry.@org.rstudio.studio.client.common.filetypes.FileTypeRegistry::satelliteEditFile(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Z)(file,position,highlightLine);
         }
      );
   }-*/;

   private final native void callSatelliteEditFile(
                                       JavaScriptObject file,
                                       JavaScriptObject position,
                                       boolean highlightLine)/*-{
      $wnd.opener.editFileFromRStudioSatellite(file, position, highlightLine);
   }-*/;


   public FileType getTypeByTypeName(String name)
   {
      return fileTypesByTypeName_.get(name);
   }




   public FileType getTypeForFile(FileSystemItem file)
   {
      // last ditch default type -- see if this either a known text file type
      // or NOT a known binary type. the result of this is
      // that unknown files types are treated as text and opened in the editor
      String defaultType = "text/plain";
      return getTypeForFile(file, defaultType);
   }

   public FileType getTypeForFile(FileSystemItem file, String defaultType)
   {
      if (file != null)
      {
         String filename = file.getName().toLowerCase();
         FileType result = fileTypesByFilename_.get(filename);
         if (result != null)
            return result;

         String extension = FileSystemItem.getExtensionFromPath(filename);
         result = fileTypesByFileExtension_.get(extension);
         if (result != null)
            return result;

         if (defaultType != null)
         {
            String mimeType = file.mimeType(defaultType);
            if (mimeType.startsWith("text/"))
               return TEXT;
         }
      }

      return null;
   }

   public TextFileType getTextTypeForFile(FileSystemItem file)
   {
      FileType type = getTypeForFile(file);
      if (type != null && type instanceof TextFileType)
         return (TextFileType) type;
      else
         return TEXT;
   }

   public FileIcon getIconForFile(FileSystemItem file)
   {
      if (file.isDirectory())
      {
         if (file.isPublicFolder())
            return FileIcon.PUBLIC_FOLDER_ICON;
         else
            return FileIcon.FOLDER_ICON;
      }

      return getIconForFilename(file.getName());
   }

   public FileIcon getIconForFilename(String filename)
   {
      FileIcon icon = iconsByFilename_.get(filename.toLowerCase());
      if (icon != null)
         return icon;
      
      String ext = FileSystemItem.getExtensionFromPath(filename);
      icon = iconsByFileExtension_.get(ext.toLowerCase());
      if (icon != null)
         return icon;

      return TEXT.getDefaultFileIcon();
   }

   private void register(String filespec, FileType fileType, ImageResource icon)
   {
      if (filespec.startsWith("*."))
      {
         String ext = StringUtil.substring(filespec, 1).toLowerCase();
         if (ext.equals("."))
         {
            ext = "";
         }
         fileTypesByFileExtension_.put(ext, fileType);
         if (icon != null)
         {
            iconsByFileExtension_.put(
                  ext,
                  new FileIcon(icon, fileType.getDefaultFileIcon().getDescription()));
         }
      }
      else if (filespec.length() == 0)
      {
         fileTypesByFileExtension_.put("", fileType);
         if (icon != null)
         {
            iconsByFileExtension_.put(
                  "",
                  new FileIcon(icon, fileType.getDefaultFileIcon().getDescription()));
         }
      }
      else
      {
         assert filespec.indexOf("*") < 0 : "Unexpected filespec format";
         fileTypesByFilename_.put(filespec.toLowerCase(), fileType);
         if (icon != null)
         {
            iconsByFilename_.put(filespec.toLowerCase(),
                  new FileIcon(icon,
                        fileType.getDefaultFileIcon().getDescription()));
         }
      }
   }

   private void registerIcon(String extension, FileIcon icon)
   {
      iconsByFileExtension_.put(extension, icon);
   }
   private final HashMap<String, FileType> fileTypesByFileExtension_ = new HashMap<>();
   private final HashMap<String, FileType> fileTypesByFilename_ = new HashMap<>();
   private final HashMap<String, FileType> fileTypesByTypeName_ = new HashMap<>();
   private final HashMap<String, FileIcon> iconsByFileExtension_ = new HashMap<>();
   private final HashMap<String, FileIcon> iconsByFilename_ = new HashMap<>();
   private final EventBus eventBus_;
   private final Satellite satellite_;
   private final Session session_;
   private final GlobalDisplay globalDisplay_;
   private final FilesServerOperations server_;
}
