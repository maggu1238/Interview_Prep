/*Given a list of 𝑁 videos, each associated with k tags, determine all similar videos for a given video 𝑖 by checking the videos from index 0 to 𝑖 − 1 . Two videos are considered similar if they share at least one tag in common.

Keep in mind N >>>>> k.*/

#include <bits/stdc++.h>
using namespace std;

// Function to find all similar videos for all videos
vector<vector<int>> findSimilarVideosForAll(int N, vector<unordered_set<int>>& videoTags) {
    unordered_map<int, unordered_set<int>> tagToVideos;  // Maps a tag to all videos that have that tag
    vector<vector<int>> similarVideos(N);  // Store similar videos for all   videos

    // Build the tag-to-video map
    for (int i = 0; i < N; i++) {
        for (int tag : videoTags[i]) {
            tagToVideos[tag].insert(i);
        }
    }

    // For each video, find similar videos based on shared tags
    for (int i = 0; i < N; i++) {
        unordered_set<int> added;  // Set to avoid duplicates
        for (int tag : videoTags[i]) {
            if (tagToVideos.find(tag) != tagToVideos.end()) {
                // Add all videos that share this tag, excluding video i itself
                for (int video : tagToVideos[tag]) {
                    if (video != i && added.find(video) == added.end()) {
                        similarVideos[i].push_back(video);
                        added.insert(video);  // Mark this video as added
                    }
                }
            }
        }
    }

    return similarVideos;
}

int main() {
    int N = 5;  // Number of videos
    vector<unordered_set<int>> videoTags = {
        {1, 2, 3},  // Video 0 has tags 1, 2, 3
        {2, 4},     // Video 1 has tags 2, 4
        {3, 5},     // Video 2 has tags 3, 5
        {1, 4},     // Video 3 has tags 1, 4
        {5, 6}      // Video 4 has tags 5, 6
    };

    // Find similar videos for all videos
    vector<vector<int>> similar = findSimilarVideosForAll(N, videoTags);

    // Print similar videos for all videos
    for (int i = 0; i < N; i++) {
        cout << "Videos similar to video " << i << ": ";
        for (int video : similar[i]) {
            cout << video << " ";
        }
        cout << endl;
    }

    return 0;
}

