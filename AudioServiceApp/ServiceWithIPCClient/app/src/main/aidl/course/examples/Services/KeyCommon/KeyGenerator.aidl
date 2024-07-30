package course.examples.Services.KeyCommon;

// Define the methods shared between both server and client
interface KeyGenerator {
    void playClip(String clipName);
    void pauseClip();
    void resumeClip();
    void stopClip();
}