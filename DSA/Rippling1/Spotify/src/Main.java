import java.util.*;

class Song {
    private final int id;
    private final String title;
    private final Set<Integer> uniqueListeners;
    private int playCount;

    public Song(int id, String title) {
        this.id = id;
        this.title = title;
        this.uniqueListeners = new HashSet<>();
        this.playCount = 0;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void play(int userId) {
        if (uniqueListeners.add(userId)) {
            playCount++;
        }
    }
}

class User {
    private final int id;
    private final Set<Integer> starredSongs;
    private final DoublyLinkedList lastNStarredSongs;
    private final int N = 5; // Maximum recent starred songs

    public User(int id) {
        this.id = id;
        this.starredSongs = new HashSet<>();
        this.lastNStarredSongs = new DoublyLinkedList();
    }

    public int getId() {
        return id;
    }

    public void starSong(int songId) {
        starredSongs.add(songId);
        lastNStarredSongs.addSong(songId);
        if (lastNStarredSongs.size() > N) {
            lastNStarredSongs.removeLast();
        }
    }

    public void unstarSong(int songId) {
        starredSongs.remove(songId);
        lastNStarredSongs.removeSong(songId);
    }

    public boolean hasStarredSong(int songId) {
        return starredSongs.contains(songId);
    }

    public void printLastNStarredSongs(Map<Integer, Song> songMap) {
        lastNStarredSongs.printList(songMap);
    }
}

class MusicPlayer {
    private int songCounter = 1;
    private final Map<Integer, Song> songs = new HashMap<>();
    private final Map<Integer, User> users = new HashMap<>();
    private int maxPlays = 0;
    private final PriorityQueue<Song> maxHeap;

    public MusicPlayer() {
        this.maxHeap = new PriorityQueue<>(Comparator.comparingInt(Song::getPlayCount).reversed());
    }

    public int addSong(String songTitle) {
        Song song = new Song(songCounter, songTitle);
        songs.put(songCounter, song);
        return songCounter++;
    }

    public void registerUser(int userId) {
        users.putIfAbsent(userId, new User(userId));
    }

    public void playSong(int songId, int userId) {
        if (!songs.containsKey(songId)) {
            System.out.println("Song ID " + songId + " not found.");
            return;
        }
        if (!users.containsKey(userId)) {
            System.out.println("User ID " + userId + " not found.");
            return;
        }

        Song song = songs.get(songId);
        User user = users.get(userId);
        song.play(userId);
        maxPlays = Math.max(maxPlays, song.getPlayCount());
        maxHeap.offer(song);

        if (user.hasStarredSong(songId)) {
            user.starSong(songId);
        }
    }

    public void printMostPlayedSongs() {
        if (maxPlays == 0) {
            System.out.println("No songs played yet.");
            return;
        }

        List<Song> mostPlayedSongs = new ArrayList<>();
        for (Song song : songs.values()) {
            if (song.getPlayCount() == maxPlays) {
                mostPlayedSongs.add(song);
            }
        }

        for (Song song : mostPlayedSongs) {
            System.out.println(song.getTitle() + " - " + maxPlays + " unique listeners");
        }
    }

    public void starSong(int userId, int songId) {
        if (!users.containsKey(userId)) {
            System.out.println("User not found.");
            return;
        }
        if (!songs.containsKey(songId)) {
            System.out.println("Song not found.");
            return;
        }
        users.get(userId).starSong(songId);
    }

    public void unstarSong(int userId, int songId) {
        if (users.containsKey(userId)) {
            users.get(userId).unstarSong(songId);
        }
    }

    public void getLastNStarredSongsPlayed(int userId) {
        if (!users.containsKey(userId)) {
            System.out.println("User not found.");
            return;
        }
        users.get(userId).printLastNStarredSongs(songs);
    }
}

class SongNode {
    int songId;
    SongNode prev, next;

    public SongNode(int songId) {
        this.songId = songId;
    }
}

class DoublyLinkedList {
    private SongNode head, tail;
    private int size;
    private final Map<Integer, SongNode> songNodeMap;

    public DoublyLinkedList() {
        this.head = new SongNode(-1); // Dummy head
        this.tail = new SongNode(-1); // Dummy tail
        head.next = tail;
        tail.prev = head;
        this.size = 0;
        this.songNodeMap = new HashMap<>();
    }

    public void addSong(int songId) {
        if (songNodeMap.containsKey(songId)) {
            moveToFront(songId);
            return;
        }

        SongNode newNode = new SongNode(songId);
        songNodeMap.put(songId, newNode);
        moveToHead(newNode);
        size++;
    }

    public void moveToFront(int songId) {
        if (!songNodeMap.containsKey(songId)) return;
        SongNode node = songNodeMap.get(songId);
        removeNode(node);
        moveToHead(node);
    }

    private void moveToHead(SongNode node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public void removeSong(int songId) {
        if (songNodeMap.containsKey(songId)) {
            removeNode(songNodeMap.get(songId));
            songNodeMap.remove(songId);
            size--;
        }
    }

    private void removeNode(SongNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void removeLast() {
        if (size == 0) return;
        SongNode lastNode = tail.prev;
        removeNode(lastNode);
        songNodeMap.remove(lastNode.songId);
        size--;
    }

    public void printList(Map<Integer, Song> songMap) {
        SongNode current = head.next;
        while (current != tail) {
            System.out.print(songMap.get(current.songId).getTitle() + " | ");
            current = current.next;
        }
        System.out.println();
    }

    public int size() {
        return size;
    }
}
