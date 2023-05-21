package uns.ftn.projekat.svt2023.service;

import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import javax.swing.text.html.*;
import java.util.*;

public interface GroupService {
    Group create(GroupDTO groupDTO);
    Group save(Group group);
    Optional<Group> delete(Integer id);
    Group findOne(Integer id);
    List<Group> findAll();
}