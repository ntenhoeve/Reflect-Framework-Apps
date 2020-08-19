package nth.sysmac.user.alarms.generator.dom.sysmac.project.xml.datatype.namespace;

import java.util.ArrayList;
import java.util.List;

import nth.sysmac.user.alarms.generator.dom.sysmac.project.xml.XmlFile;

public class NameSpaceXmlFiles extends ArrayList<NameSpaceXmlFile> {

	private static final long serialVersionUID = -5950954209975268527L;

	public NameSpaceXmlFiles(List<XmlFile> xmlFiles) {
		for ( XmlFile xmlFile : xmlFiles) {
			boolean isNameSpaceXmlFile = xmlFile.containsNode(NameSpaceXmlFile.X_PATH);
			if ( isNameSpaceXmlFile) {
				NameSpaceXmlFile nameSpaceXmlFile=new NameSpaceXmlFile(xmlFile);
				add(nameSpaceXmlFile);
			}
		}
	}
}
