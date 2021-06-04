package conf;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebAppInizializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext sc) throws ServletException {		
		
		System.out.println("autodetect ");
		
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(libreriaConfig.class);
		
		
		ServletRegistration.Dynamic reg = sc.addServlet("dispatcher", new DispatcherServlet(context));
		reg.setLoadOnStartup(1);
		
		reg.addMapping("/libreria/*");
	}

}