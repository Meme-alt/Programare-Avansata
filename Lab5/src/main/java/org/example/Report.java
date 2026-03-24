package org.example;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.awt.Desktop;

public class Report extends Command{
    private String file = "report.html";
    public Report(Catalog catalog){
        super(catalog);
    }
    @Override
    public void execute() throws Exception{
        Configuration config = new Configuration();
        config.setDefaultEncoding("UTF-8");
        config.setDirectoryForTemplateLoading(new File("src/main/java/org/example"));
        Map<String, Object> root = new HashMap<>();
        root.put("name", catalog.getName());
        root.put("resources", catalog.getResources());
        Template temp = config.getTemplate("report.ftl");
        try(Writer out = new FileWriter(file)){
            temp.process(root, out);
        }
        if(Desktop.isDesktopSupported()){
            Desktop.getDesktop().open(new File(file));
        }
    }
}
