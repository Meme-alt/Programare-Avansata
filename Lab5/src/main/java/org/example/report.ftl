<html>
<body>
 <h1>Catalog: ${name}</h1>
 <table border="1">
  <tr>
   <th>ID</th>
   <th>Title</th>
   <th>Author</th>
   <th>Year</th>
  </tr>
  <#list resources as res>
  <tr>
   <td>${res.id}</td>
   <td>${res.title}</td>
   <td>${res.author}</td>
   <td>${res.year}</td>
  </tr>
  </#list>
 </table>
</body>
</html>