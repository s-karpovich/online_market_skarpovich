package by.tut.mdcatalog.project2.service.model;

import javax.validation.constraints.NotNull;

public class RoleDTOUpdated {

    @NotNull
    private Long roleId;
    @NotNull
    private Long id;

    public RoleDTOUpdated() {
    }

    public RoleDTOUpdated(Long roleId, Long id) {
        this.roleId = roleId;
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}