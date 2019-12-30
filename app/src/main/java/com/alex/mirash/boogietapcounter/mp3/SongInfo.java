package com.alex.mirash.boogietapcounter.mp3;

/**
 * @author Mirash
 */
public class SongInfo {
    private String title;
    private int position;
    private int totalCount;
    private int bpm;
    private long duration;

    public SongInfo(String title, int position, int totalCount, long duration, int bpm) {
        if (title != null && title.endsWith(".mp3")) {
            title = title.substring(0, title.length() - 4);
        }
        this.title = title;
        this.position = position;
        this.totalCount = totalCount;
        this.duration = duration;
        this.bpm = bpm;
    }

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public int getBpm() {
        return bpm;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "SongInfo{" +
                "title='" + title + '\'' +
                ", position=" + position +
                ", totalCount=" + totalCount +
                ", duration=" + duration +
                ", bpm=" + bpm +
                '}';
    }
}
