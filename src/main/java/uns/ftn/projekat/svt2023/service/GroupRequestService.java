package uns.ftn.projekat.svt2023.service;

import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;

public interface GroupRequestService {
    GroupRequest create(Integer groupId);
    void approveRequest(Integer requestId);
    void declineRequest(Integer requestId);
}
