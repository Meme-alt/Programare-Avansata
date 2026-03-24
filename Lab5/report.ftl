<html>
<body>
 <h1>Catalog: Test</h1>
 <table border="1">
  <tr>
   <th>ID</th>
   <th>Title</th>
   <th>Author</th>
   <th>Year</th>
  </tr>
  <tr>
   <td>FreeMarker template error (DEBUG mode; use RETHROW in production!):
The following has evaluated to null or missing:
==> res  [in template "report.ftl" at line 12, column 10]

----
Tip: If the failing expression is known to legally refer to something that's sometimes null or missing, either specify a default value like myOptionalVar!myDefault, or use <#if myOptionalVar??>when-present<#else>when-missing</#if>. (These only cover the last step of the expression; to cover the whole expression, use parenthesis: (myOptionalVar.foo)!myDefault, (myOptionalVar.foo)??
----

----
FTL stack trace ("~" means nesting-related):
	- Failed at: ${res.id}  [in template "report.ftl" at line 12, column 8]
----

Java stack trace (for programmers):
----
freemarker.core.InvalidReferenceException: [... Exception message was already printed; see it above ...]
	at freemarker.core.InvalidReferenceException.getInstance(InvalidReferenceException.java:134)
	at freemarker.core.UnexpectedTypeException.newDescriptionBuilder(UnexpectedTypeException.java:85)
	at freemarker.core.UnexpectedTypeException.<init>(UnexpectedTypeException.java:48)
	at freemarker.core.NonHashException.<init>(NonHashException.java:49)
	at freemarker.core.Dot._eval(Dot.java:48)
	at freemarker.core.Expression.eval(Expression.java:101)
	at freemarker.core.DollarVariable.calculateInterpolatedStringOrMarkup(DollarVariable.java:100)
	at freemarker.core.DollarVariable.accept(DollarVariable.java:63)
	at freemarker.core.Environment.visit(Environment.java:347)
	at freemarker.core.Environment.visit(Environment.java:353)
	at freemarker.core.Environment.process(Environment.java:326)
	at freemarker.template.Template.process(Template.java:383)
	at org.example.Report.execute(Report.java:27)
	at org.example.Main.main(Main.java:13)
