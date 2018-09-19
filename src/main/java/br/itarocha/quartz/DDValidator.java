package br.itarocha.quartz;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class DDValidator {
	
	private static final String XSD_ACCESS_ITENS = "xsd/accessItens.xsd";
	private static final String XSD_CONTROL = "xsd/control.xsd";
	private static final String XSD_I18N = "xsd/i18n.xsd";
	private static final String XSD_INSTANCE = "xsd/instance.xsd";
	private static final String XSD_SYSTEM_SCRIPTS = "xsd/systemScripts.xsd";
	private static final String XSD_TABLE = "xsd/table.xsd";

	
	private List<SchemaError> schemaErrors;
	
	public static void main(String[] args) {
		DDValidator ddv = new DDValidator();
		ddv.testar();
	}
	
	public DDValidator() {
		schemaErrors = new ArrayList<SchemaError>();
	}
	
	public void testar() {
		testarXmlFile(XSD_ACCESS_ITENS, "D:/Itamar/xsd/original/ItensAcessos.xml"); // PASSED
		testarXmlFile(XSD_SYSTEM_SCRIPTS , "D:/Itamar/xsd/original/ScriptsSistema.xml"); // PASSED
		testarXmlFile(XSD_I18N, "D:/Itamar/xsd/original/TermosI18n.xml"); // PASSED
		
		testarXmlDir(XSD_CONTROL, "D:/Itamar/xsd/original/controles"); // PASSED
		testarXmlDir(XSD_TABLE, "D:/Itamar/xsd/original/tabelas"); // PASSED
		testarXmlDir(XSD_INSTANCE, "D:/Itamar/xsd/original/instancias"); // PASSED

		for (SchemaError e : schemaErrors) {
			System.out.println(e);
		}
		
		
		//testarXml(new File("D:/Itamar/xsd/schemas/controles_v2.xsd"), "D:/Itamar/xsd/controles"); // PASSED
		//testarXml(new File("D:/Itamar/xsd/schemas/tabelas_v2.xsd"), "D:/Itamar/xsd/tabelas"); // PASSED
		//testarXml(new File("D:/Itamar/xsd/schemas/instancias_v2.xsd"), "D:/Itamar/xsd/instancias"); // PASSED
	}
	
    public static boolean validateXMLSchema(File xsdFile, String xmlPath){
    	try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
			return true;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
	
    /*
    private boolean validateXMLSchema(String resource, File xmlPath){
    	try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            InputStream in = this.getClass().getResourceAsStream(resource);
            Schema schema = factory.newSchema(new StreamSource(in));
            Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xmlPath));
			return true;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("*******************");
			System.out.println(e.getMessage());
			System.out.println("*******************");
			
			//e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
	*/
	private void testarXmlFile(String resource, String path) {
		System.out.println(String.format("Testando [%s] - arquivo [%s]...", resource, path));
		File f = new File(path);
		SchemaError err = validateXMLSchema(resource, new File(f.getAbsolutePath()));
		if (err != null) {
			schemaErrors.add(err);
		}
	} 
    
	private void testarXmlDir(String resource, String path) {
		int qtdErros = 0;
		System.out.println(String.format("Testando [%s] - diretório [%s]...", resource, path));
		File file = new File(path);
		File[] files = file.listFiles();
		for (File f :  files) {
			SchemaError err = validateXMLSchema(resource, new File(f.getAbsolutePath()) ); 
			if (err != null) {
				schemaErrors.add(err);
			}
		}
		System.out.println(String.format("FIM. Testados [%s] %d arquivos. Erros: %d\n\n", resource, files.length, qtdErros));
	} 
    
    /*
	private void testarXml(File xsdFile, String path) {
		int qtdErros = 0;
		File file = new File(path);
		File[] files = file.listFiles();
		for (File f :  files) {
			SchemaError err = validateXMLSchema(xsdFile, f.getAbsolutePath());
			if (err != null) {
				schemaErrors.add(err);
			}

			
			if (!validateXMLSchema(xsdFile, f.getAbsolutePath())) {
				qtdErros++;
				System.out.println(String.format("ERRO em [%s] ...",f.getAbsolutePath()));
			}
		}
		System.out.println(String.format("\nFIM. Testados %d arquivos. Erros: %d",files.length, qtdErros));
	}
	*/
	
    private SchemaError validateXMLSchema(String resource, File xmlPath){
    	SchemaError error = null;
    	InputStream in = null;
    	try {
	        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        in = this.getClass().getResourceAsStream(resource);
	        Schema schema = factory.newSchema(new StreamSource(in));
	        Validator validator = schema.newValidator();
				validator.validate(new StreamSource( xmlPath ));
		} catch (SAXException e) {
			error = new SchemaError(  xmlPath.getAbsolutePath(), e.getMessage() );
		} catch (IOException e) {
			error = new SchemaError(  xmlPath.getAbsolutePath(), e.getMessage() );
		} finally {
    		if (in != null) {
    			try {
					in.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
    		}
    	}
    	return error;
    }
	
	
	private class SchemaError {
		private String xmlFile;
		private String errorMessage;
		
		public SchemaError(String xmlFile, String errorMessage) {
			this.xmlFile = xmlFile;
			this.errorMessage = errorMessage;
		}
		
		@Override
		public String toString() {
			return String.format("Erro ao validar schema do arquivo [%s]. Mensagem: [%s]", this.xmlFile, this.errorMessage);
		}
	}
	
}
