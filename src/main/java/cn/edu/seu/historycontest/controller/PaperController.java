package cn.edu.seu.historycontest.controller;


import cn.edu.seu.historycontest.payload.CalibrateTimeRequest;
import cn.edu.seu.historycontest.payload.DetailedPaper;
import cn.edu.seu.historycontest.security.CurrentUser;
import cn.edu.seu.historycontest.security.UserPrincipal;
import cn.edu.seu.historycontest.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @GetMapping("generate")
    @PreAuthorize("hasRole('STUDENT') and hasAuthority('STATUS_NOT_START')")
    public DetailedPaper generatePaper(@CurrentUser UserPrincipal userPrincipal) {
        return paperService.generatePaper(userPrincipal);
    }

    @PostMapping("calibrate")
    @PreAuthorize("hasRole('STUDENT') and hasAuthority('STATUS_GENERATED')")
    public void calibrateTime(@CurrentUser UserPrincipal userPrincipal, @RequestBody CalibrateTimeRequest calibrateTimeRequest) {
        paperService.calibrateTime(userPrincipal.getId(), calibrateTimeRequest.getTime());
    }

    @GetMapping
    @PreAuthorize("hasRole('STUDENT') and hasAnyAuthority('STATUS_STARTED', 'STATUS_SUBMITTED')")
    public DetailedPaper getPaper(@CurrentUser UserPrincipal userPrincipal) {
        return paperService.getDetailedPaper(userPrincipal.getId());
    }

}

