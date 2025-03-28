package models;

public class MeetingRoomFilter {

    private Boolean hasProjector = false;
    private Boolean hasWhiteBoard = false;

    private MeetingRoomFilter(Builder builder){
        this.hasProjector = builder.hasProjector;
        this.hasWhiteBoard = builder.hasWhiteBoard;
    }

    public Boolean getHasProjector() {
        return  hasProjector;
    }

    public Boolean getHasWhiteBoard() {
        return hasWhiteBoard;
    }

    public static class Builder {
        private boolean hasProjector;
        private boolean hasWhiteBoard;

        public Builder setHasProjector(boolean hasProjector){
            this.hasProjector = hasProjector;
            return  this;
        }


        public Builder setHasWhiteboard(boolean hasWhiteboard){
            this.hasWhiteBoard = hasWhiteboard;
            return  this;
        }

        public MeetingRoomFilter build() {
            return new MeetingRoomFilter(this);
        }

    }


}
