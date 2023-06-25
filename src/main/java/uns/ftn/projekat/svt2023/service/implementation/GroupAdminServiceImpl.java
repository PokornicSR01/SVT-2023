package uns.ftn.projekat.svt2023.service.implementation;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.util.*;

@Service
public class GroupAdminServiceImpl implements GroupAdminService {

    @Autowired
    private GroupAdminRepository groupAdminRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupServiceImpl groupService;
    @Autowired
    private UserService userService;


    @Override
    public GroupAdmin create(GroupAdminDTO groupAdminDTO) {
        GroupAdmin groupAdmin = new GroupAdmin();
        groupAdmin.setUser(userService.findOne(groupAdminDTO.getId()));
        groupAdmin.setId(groupAdmin.getId());
        groupAdmin = groupAdminRepository.save(groupAdmin);

        return groupAdmin;
    }

    @Override
    public GroupAdmin save(GroupAdmin groupAdmin) {return groupAdminRepository.save(groupAdmin);}
    @Override
    public void delete(Integer id) {
    }
    @Override
    public List<GroupAdmin> findAll() {
        return this.groupAdminRepository.findAll();
    }
    @Override
    public GroupAdmin findOne(Integer id) {
        return null;
    }
}
