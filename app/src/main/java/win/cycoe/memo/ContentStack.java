package win.cycoe.memo;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by cycoe on 7/31/17.
 */

public class ContentStack {

    private int maxNum;
    private int currentPoint;
    private int stackDepth;
    private String[] contentList;
    private int[] cursorStartList;
    private int[] cursorCountList;
    private Map<String, Object> map;

    public ContentStack(int _maxNum) {
        maxNum = _maxNum;
        currentPoint = 0;
        stackDepth = 0;
        contentList = new String[maxNum];
        cursorStartList = new int[maxNum];
        cursorCountList = new int[maxNum];
        map = new HashMap<String, Object>();
    }

    public void put(String content, int cursorStart, int cursorCount) {
        for(int i = 0; i < stackDepth - currentPoint; i++) {
            contentList[i] = contentList[i + currentPoint];
            cursorStartList[i] = cursorStartList[i + currentPoint];
            cursorCountList[i] = cursorCountList[i + currentPoint];
        }
        stackDepth = stackDepth - currentPoint;
        currentPoint = 0;
        for(int i = maxNum - 1; i > 0; i--) {
            contentList[i] = contentList[i - 1];
            cursorStartList[i] = cursorStartList[i - 1];
            cursorCountList[i] = cursorCountList[i - 1];
        }
        contentList[0] = content;
        cursorStartList[0] = cursorStart;
        cursorCountList[0] = cursorCount;
        if(stackDepth < maxNum)
            stackDepth++;
    }

    public boolean canUndo() {
        return currentPoint + 1 < stackDepth;
    }

    public boolean canRedo() {
        return currentPoint - 1 >= 0;
    }

    public Map<String, Object> undo() {
        ++currentPoint;
        packMap();
        return map;
    }

    public Map<String, Object> redo() {
        --currentPoint;
        packMap();
        return map;
    }

    private void packMap() {
        map.put("content", contentList[currentPoint]);
        map.put("cursorStart", cursorStartList[currentPoint]);
        map.put("cursorCount", cursorCountList[currentPoint]);
    }
}