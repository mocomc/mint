package pep.per.mint.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TreeUtil {

    private final Map<String, String> nodeParent = new HashMap<String, String>();
    private final LinkedHashSet<String> nodeList = new LinkedHashSet<String>();
    private int depth = 0;

    private void checkNotNull(String node, String parameterName) {
        if (node == null)
            throw new IllegalArgumentException(parameterName + " must not be null");
    }


    /**
     * Node Add
     * @param parent
     * @param node
     * @return
     */
    public boolean add(String parent, String node) {
        checkNotNull(parent, "parent");
        checkNotNull(node, "node");

		//----------------------------------------------------------------
		// Parent Add
        // Parent 가 없을 경우만 추가한다(오류입력으로 인한 무한루프 방지)
		//----------------------------------------------------------------
        String parentNode = getParent(node);
        if( parentNode == null ) {
        	nodeParent.put(node, parent);
        	nodeList.add(parent);
        } else {
        	if( parentNode.toString().equals( node.toString() ) ) {

        	}
        }
		//----------------------------------------------------------------
		// Child Add
		//----------------------------------------------------------------
        boolean added = nodeList.add(node);

        return added;
    }

    /**
     * Node Remove
     * @param node
     * @param cascade
     * @return
     */
    public boolean remove(String node, boolean cascade)  throws Exception{
        checkNotNull(node, "node");

        if (!nodeList.contains(node)) {
            return false;
        }
        if (cascade) {
            for (String child : getChildren(node)) {
                remove(child, true);
            }
        } else {
            for (String child : getChildren(node)) {
                nodeParent.remove(child);
            }
        }
        nodeList.remove(node);
        return true;
    }

    /**
     * Root Node Return
     * @return
     */
    public List<String> getRoots() throws Exception {
        return getChildren(null);
    }

    /**
     * Node Depth
     * @param node
     * @return
     */
    public int getHeight(String node) {
    	depth = 0;
    	checkNotNull(node, "node");
    	heightCount(node);
        return depth;
    }

    /**
     * Depth Count
     * @param node
     */
    private void heightCount(String node) {
    	if(getParent(node) != null) {
    		if( node.equals( getParent(node) ) ) {
    			return;
    		} else {
    			depth++;
    			heightCount(getParent(node));
    		}
//    		depth++;
//    		heightCount(getParent(node));
    	}
    }

    /**
     * Parent Node Return
     * @param node
     * @return
     */
    public String getParent(String node) {
        checkNotNull(node, "node");
        return nodeParent.get(node);
    }

    /**
     * Child Node Return
     * @param node
     * @return
     */
    public List<String> getChildren(String node) throws Exception{
        List<String> children = new LinkedList<String>();
        for (String n : nodeList) {
        	String parent = nodeParent.get(n);
        	if( node == null ) {
        		if( parent.equals(n) ) {
        			children.add(n);
        		}
        	} else {
        		if( parent == null ) {
        			throw new Exception("계층구조가 잘못 정의 되어 있습니다. [" + n + "] 의 부모 노드가 없습니다.\n[" + n + "] (이)가 최상위 노드 이면, Parent-Child 에 동일한 값으로 적어도 하나 설정 하시기 바랍니다.");
        		}
        		if( parent.equals(n) ) {
        			continue;
        		}

        		if( parent !=null && parent.equals(node) ) {
        			children.add(n);
        		}
        	}

/*
            if (node == null && parent == null) {
                children.add(n);
            } else if (node != null && parent != null && parent.equals(node)) {
                children.add(n);
            }
*/
        }
        return children;
    }


    public List<Map<String,String>> getTreeNode() throws Exception{
    	List<Map<String,String>> nodeList = new ArrayList<Map<String,String>>();
    	for(String node : this.nodeList) {
    		Map<String,String> nodeInfo = new HashMap<String,String>();
    		String parent    = getParent(node);
    		String isRoot  = parent.equals(node) ? "Y" : "N";
    		String isGroup = getChildren(node).isEmpty() ? "N" : "Y";
    		String depth = String.valueOf(getHeight(node));

    		nodeInfo.put("parent", parent);
    		nodeInfo.put("node", node);
    		nodeInfo.put("isRoot", isRoot);
    		nodeInfo.put("isGroup", isGroup);
    		nodeInfo.put("depth", depth);

    		nodeList.add(nodeInfo);
    	}
    	return nodeList;
    }

    public List<NodeInfo> getNodeList() throws Exception{
    	List<NodeInfo> nodeList = new ArrayList<NodeInfo>();

    	for(String node : this.nodeList) {
    		NodeInfo nodeInfo = new NodeInfo();
    		String parent    = getParent(node);
    		String rootYn  = parent.equals(node) ? "Y" : "N";
    		String groupYn = getChildren(node).isEmpty() ? "N" : "Y";
    		int depth = getHeight(node);

    		nodeInfo.setName(node);;
    		nodeInfo.setParentName(parent);
    		nodeInfo.setRootYn(rootYn);
    		nodeInfo.setGroupYn(groupYn);
    		nodeInfo.setDepth(depth);

    		nodeList.add(nodeInfo);
    	}

    	return nodeList;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        try {
        	dumpNodeStructure(builder, null, "- ");
        } catch( Exception e ) {
        	e.printStackTrace();
        }
        return builder.toString();
    }

    private void dumpNodeStructure(StringBuilder builder, String node, String prefix) throws Exception {
        if (node != null) {
            builder.append(prefix);
            builder.append(node.toString());
            builder.append('\n');
            prefix = "    " + prefix;
        }
        for (String child : getChildren(node)) {
            dumpNodeStructure(builder, child, prefix);
        }
    }

    public class NodeInfo {
    	private String name = "";
    	private String parentName = "";
    	private String rootYn  = "N";
    	private String groupYn = "N";
    	private int depth = 0;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getParentName() {
			return parentName;
		}
		public void setParentName(String parentName) {
			this.parentName = parentName;
		}
		public String getRootYn() {
			return rootYn;
		}
		public void setRootYn(String rootYn) {
			this.rootYn = rootYn;
		}
		public String getGroupYn() {
			return groupYn;
		}
		public void setGroupYn(String groupYn) {
			this.groupYn = groupYn;
		}
		public int getDepth() {
			return depth;
		}
		public void setDepth(int depth) {
			this.depth = depth;
		}
    }

}