package com.example.trend.service;

import com.example.trend.model.TrendData;
import com.example.trend.utils.ExcelReader;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
public class TrendService {

    private static final Logger logger = Logger.getLogger(TrendService.class.getName());

    public TrendData getTrends(String filePath) throws IOException {
        try (InputStream file = getClass().getResourceAsStream(filePath)) {
            if (file == null) {
                throw new IOException("File not found: " + filePath);
            }
            List<List<Double>> trends = ExcelReader.readTrends(file);
            return new TrendData(trends.get(0), trends.get(1));
        }
    }

    public List<String> getStablePoints(List<Double> trend, double threshold) {
        List<String> stablePoints = new ArrayList<>();
        for (int i = 1; i < trend.size(); i++) {
            double diff = Math.abs(trend.get(i) - trend.get(i - 1));
            if (diff <= threshold) {
                stablePoints.add("index " + i + ": " + trend.get(i - 1) + " -> " + trend.get(i) + " (diff: " + diff + ")");
            }
        }
        return stablePoints;
    }

    public List<String> getUnstablePoints(List<Double> trend, double threshold) {
        List<String> unstablePoints = new ArrayList<>();
        for (int i = 1; i < trend.size(); i++) {
            double diff = Math.abs(trend.get(i) - trend.get(i - 1));
            if (diff > threshold) {
                String point = "index " + i + ": " + trend.get(i - 1) + " -> " + trend.get(i) + " (diff: " + diff + ")";
                logger.info("Unstable trend detected at " + point);
                unstablePoints.add(point);
            }
        }
        return unstablePoints;
    }

    public void printTrends(TrendData trendData) {
        logger.info("Trend 1: " + trendData.getTrend1());
        logger.info("Trend 2: " + trendData.getTrend2());
    }
}
