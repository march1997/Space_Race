package graphics;

import java.util.List;
import java.util.ArrayList;

public class IRenderableHolder {
	
	private static final IRenderableHolder instance = new IRenderableHolder();
	
	private List<IRenderable> entities;
	
	public IRenderableHolder() {
		entities = new ArrayList<IRenderable>();
	}
	
	public static IRenderableHolder getInstance() {
		return instance;
	}
	
	public List<IRenderable> getEntities() {
		return entities;
	}

}
