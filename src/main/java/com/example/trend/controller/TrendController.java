package com.example.trend.controller;

import com.example.trend.model.TrendData;
import com.example.trend.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
@Controller
public class TrendController {

    @Autowired
    private TrendService trendService;

    @GetMapping("/evaluate")
    public String evaluateTrends(@RequestParam double threshold, Model model) {
        try {
            TrendData trendData = trendService.getTrends("/data/task.xlsx");
            trendService.printTrends(trendData); // Печать данных трендов для отладки

            List<String> stableTrend1Points = trendService.getStablePoints(trendData.getTrend1(), threshold);
            List<String> unstableTrend1Points = trendService.getUnstablePoints(trendData.getTrend1(), threshold);

            List<String> stableTrend2Points = trendService.getStablePoints(trendData.getTrend2(), threshold);
            List<String> unstableTrend2Points = trendService.getUnstablePoints(trendData.getTrend2(), threshold);

            model.addAttribute("stableTrend1Points", stableTrend1Points);
            model.addAttribute("unstableTrend1Points", unstableTrend1Points);

            model.addAttribute("stableTrend2Points", stableTrend2Points);
            model.addAttribute("unstableTrend2Points", unstableTrend2Points);

            return "evaluate";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Error reading file: " + e.getMessage());
            return "error";
        }
    }
}
