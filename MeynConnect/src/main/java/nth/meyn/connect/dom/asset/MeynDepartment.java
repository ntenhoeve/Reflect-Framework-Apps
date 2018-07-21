package nth.meyn.connect.dom.asset;

import java.util.List;
import java.util.stream.Collectors;

import nth.introspect.layer5provider.reflection.behavior.description.Description;

@Description(englishDescription="A department that contains one or more Meyn lines")
public class MeynDepartment extends Asset {

	
	
	public MeynDepartment(String name) {
		super(name);
	}

	public List<Asset> getMeynLines() {
		return  getAssets().stream().filter(asset->asset instanceof MeynLine).collect(Collectors.toList());
	}
}