package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.GlobalConstants;
import com.example.accessingdatamysql.entity.CrashReports;
import com.example.accessingdatamysql.entity.CrashReportsDetail;
import com.example.accessingdatamysql.service.CrashReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/crashReports") // This means URL's start with /demo (after Application path)
public class CrashReportsController {
    @Autowired
    private CrashReportService crashReportService;

    @PostMapping(value = "/add")
    public @ResponseBody void addCrashReport(@RequestParam("userId") Integer userId, @RequestParam("clientVersion") Double clientVersion, @RequestBody CrashReportsDetail crashReportDetail) {
        GlobalConstants.printIfDoDebug("CrashReportsController: received content: " + "userId: " + userId + ", clientVersion: " + clientVersion);
        crashReportService.addNew(
                clientVersion,
                userId,
                crashReportDetail.getStackTrace(),
                crashReportDetail.getDeviceInfo());
    }

    @GetMapping(value = "/get")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody CrashReports getCrashReport(@RequestParam Integer reportId) {
        return crashReportService.getOne(reportId);
    }
}
