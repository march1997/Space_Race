package graphics;

import java.util.List;
import java.util.ArrayList;

public class RenderableHolder {
	
	private static final RenderableHolder instance = new RenderableHolder();
	
	private List<IRenderable> entities;
	
	public RenderableHolder() {
		entities = new ArrayList<IRenderable>();
		
	}
	
	public static RenderableHolder getInstance() {
		return instance;
	}
	
	public List<IRenderable> getEntities() {
		return entities;
	}

}
