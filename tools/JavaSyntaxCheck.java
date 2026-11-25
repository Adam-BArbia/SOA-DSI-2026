import javax.tools.*;
import com.sun.source.util.JavacTask;
import java.util.*;
import java.nio.file.*;
import java.util.stream.*;
public class JavaSyntaxCheck {
 public static void main(String[] args) throws Exception {
   List<String> files;
   try(var stream=Files.walk(Path.of(args[0]))) {
      files=stream.filter(p->p.toString().endsWith(".java") && !p.toString().contains("/target/"))
                  .map(Path::toString).collect(Collectors.toList());
   }
   JavaCompiler compiler=ToolProvider.getSystemJavaCompiler();
   DiagnosticCollector<JavaFileObject> diagnostics=new DiagnosticCollector<>();
   try(StandardJavaFileManager manager=compiler.getStandardFileManager(diagnostics,null,null)){
     var units=manager.getJavaFileObjectsFromStrings(files);
     JavacTask task=(JavacTask) compiler.getTask(null,manager,diagnostics,List.of("-proc:none"),null,units);
     task.parse();
   }
   var errors=diagnostics.getDiagnostics().stream().filter(d->d.getKind()==Diagnostic.Kind.ERROR).toList();
   errors.forEach(d->System.err.println("ERROR "+d.getSource()+":"+d.getLineNumber()+" "+d.getMessage(Locale.ROOT)));
   System.out.println("Parsed "+files.size()+" Java files; syntax errors: "+errors.size());
   if(!errors.isEmpty())System.exit(1);
 }
}
