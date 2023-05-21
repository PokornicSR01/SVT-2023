package uns.ftn.projekat.svt2023.service.implementation;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group create(GroupDTO groupDTO) {

        Group newGroup = new Group();
        newGroup.setCreationDate(LocalDateTime.now());
        newGroup.setId(groupDTO.getId());
        newGroup.setName(groupDTO.getName());
        newGroup.setDescription(groupDTO.getDescription());
        newGroup.setIsSuspended(groupDTO.getIsSuspended());
        newGroup.setSuspendedReason("");
        newGroup = groupRepository.save(newGroup);

        return newGroup;
    }

    @Override
    public Group save(Group group) {return groupRepository.save(group);}

    @Override
    public Optional<Group> delete(Integer id) {
        Optional<Group> deletedGroup = groupRepository.findById(id);
        if(deletedGroup == null) {
            return null;
        }
        groupRepository.deleteById(id);

        return deletedGroup;
    }

    @Override
    public Group findOne(Integer id) {return groupRepository.findById(id).orElseGet(null);}

    @Override
    public List<Group> findAll() {return groupRepository.findAll();}
}
