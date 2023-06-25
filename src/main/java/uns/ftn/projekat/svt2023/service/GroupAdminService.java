package uns.ftn.projekat.svt2023.service;

import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import java.util.*;

public interface GroupAdminService {
    GroupAdmin create(GroupAdminDTO groupAdminDTO);
    GroupAdmin save(GroupAdmin groupAdmin);
    void delete(Integer id);
    List<GroupAdmin> findAll();
    GroupAdmin findOne(Integer id);
}
