package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class DiskInfo {

    /** 计量单位 */
    private String unit;
    private List<DiskVolumeInfo> diskVolumes = new ArrayList<>();

    public DiskInfo(String unit) {
        this.unit = unit;
    }

    public DiskInfo addDiskVolume(DiskVolumeInfo diskVolume) {
        diskVolumes.add(diskVolume);
        return this;
    }

    @Setter
    @Getter
    public static class DiskVolumeInfo {
        private String diskId;

        private long total;

        private long free;

        private long usable;

        public DiskVolumeInfo(String diskId) {
            this.diskId = diskId;
        }
    }
}
