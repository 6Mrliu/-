/**
 * 测试类，演示堆排序算法。
 */
public class Test {
    /**
     * 主函数，用于演示堆排序。
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        int[] R = {7, 10, 13, 15, 4, 20, 19, 8};
        int n = R.length;
        HeapSort(R, n); // 堆排序
        printArray(R); // 打印排序后的数组
    }

    /**
     * 调整堆，保证满足最大堆的性质。
     * @param R 待排序的数组
     * @param v 根节点的索引
     * @param n 数组的长度
     */
    private static void Heapify(int[] R, int v, int n) {
        int i = v;
        int j = 2 * i + 1; // 计算左子节点的索引
        int temp;

        temp = R[i];
        while (j < n) { // 判断是否有右子节点
            // 选择较大的子节点
            if (j + 1 < n && R[j] < R[j + 1]) {
                j++;
            }
            // 当根节点小于子节点时，交换位置并继续调整
            if (temp < R[j]) {
                R[i] = R[j];
                i = j;
                j = 2 * i + 1;
            } else {
                break;
            }
        }
        R[i] = temp;
    }

    /**
     * 堆排序算法。
     * @param R 待排序的数组
     * @param n 数组的长度
     */
    private static void HeapSort(int[] R, int n) {
        // 建立最大堆
        for (int i = n / 2 - 1; i >= 0; i--) { // 从最后一个非叶子节点开始调整
            Heapify(R, i, n); // 调整当前节点
        }

        // 依次取出堆顶元素，进行调整，直到排序完成
        for (int i = n - 1; i > 0; i--) {
            // 交换堆顶元素和最后一个元素
            int temp = R[0];
            R[0] = R[i];
            R[i] = temp;

            // 调整剩余元素，保证堆的性质
            Heapify(R, 0, i);
        }
    }

    /**
     * 打印数组元素。
     * @param arr 要打印的数组
     */
    private static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
