package sample;

import java.util.ArrayList;
import java.util.List;

public class TableFormat {

    public List<List<String>> data;

    public String println(Integer interval) {
        StringBuilder stringBuilder = new StringBuilder();
        Integer width = data.get(0).size();
        Integer high = data.size();
        Integer[] maxWidths = getMaxWidth();
        for (int i = 0; i < high; i++) {
            for (int y = 0; y < width; y++) {
                String text = data.get(i).get(y);
                Integer maxWidth = maxWidths[y];
                if (y > 0) {
                    maxWidth+=interval;
                }
                stringBuilder.append(getPlace(text, maxWidth));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getPlace(String text, Integer maxWidth) {
        int textSize = text.length();
        for (int i = 0; i < maxWidth - textSize; i++) {
            text = " " + text;
        }
        return text;
    }

    /**
     * 计算每一列每行内容的最大长度
     */
    public Integer[] getMaxWidth() {
        Integer width = data.get(0).size();
        Integer high = data.size();
        Integer[] widthArray = new Integer[width];
        for (int w = 0; w < width; w++) {
            String[] array = new String[high];
            for (int h = 0; h < high; h++) {
                array[h] = data.get(h).get(w);
            }
            widthArray[w] = getLengthMax(array);
        }
        return widthArray;
    }

    /**
     * 获取数组字符串中长度最大的值
     */
    public Integer getLengthMax(String[] arr) {
        Integer max = arr[0].length();
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].length() > max) {
                max = arr[i].length();
            }
        }
        return max;
    }

    public TableFormat(List<List<String>> data) {
        this.data = data;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
