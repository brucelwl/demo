package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class DiskInfo {

    private List<DiskVolumeInfo> m_diskVolumes = new ArrayList<>();

    public DiskInfo addDiskVolume(DiskVolumeInfo diskVolume) {
        m_diskVolumes.add(diskVolume);
        return this;
    }

    @Setter
    @Getter
    public static class DiskVolumeInfo {
        private String m_id;

        private long m_total;

        private long m_free;

        private long m_usable;


    }
}
