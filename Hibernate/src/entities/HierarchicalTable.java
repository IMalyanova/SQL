package entities;

/**
 * Created by Danya on 26.10.2015.
 */
public class HierarchicalTable {
    private Integer id;
    private Integer parentId;
    private String name;

    public HierarchicalTable() {
        //Used by Hibernate
    }

    public HierarchicalTable(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {      return parentId;   }
    public void setParentId(Integer parentId) {   this.parentId = parentId; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
