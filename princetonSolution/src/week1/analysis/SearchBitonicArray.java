package week1.analysis;

public class SearchBitonicArray {
    //if exists output compare times with positive, otherwise with negative
    //input length must >= 3, and is bitonic
    public static int solve3lgn(int[] arr, int tar) {
        //find peak
        int s = 0, e = arr.length - 1, cnt = 0;
        while (s <= e) {
            int mid = s + (e - s) / 2;
            if (arr[mid] > arr[mid + 1]) {
                e = mid - 1;
            } else {
                s =  mid + 1;
            }
            cnt++;
        }
        int peak = e + 1;
        int findLeftCompareCnt = binarySearch(arr,0,peak,tar,true);
        int findRightCompareCnt = binarySearch(arr,peak,arr.length - 1,tar,false);
        boolean find = findLeftCompareCnt > 0 || findRightCompareCnt > 0;
        int res = cnt + Math.abs(findLeftCompareCnt) + Math.abs(findRightCompareCnt);
        return find ? res : -res;
    }

    public static int solve2lgn(int[] arr, int tar) {
        int s = 0, e = arr.length - 1, cnt = 0;
        boolean find = false;
        while (s <= e) {
            int mid = s + (e - s) / 2;
            boolean peakOnLeft = arr[mid] < arr[mid - 1];
            cnt++;
            if (tar == arr[mid]) {
                find = true;
                break;
            }
            cnt++;
            if (tar < arr[mid]) {
                int findLeftCompareCnt = binarySearch(arr,s,mid,tar,true);
                int findRightCompareCnt = binarySearch(arr,mid,e,tar,false);
                find = (findLeftCompareCnt > 0 || findRightCompareCnt > 0);
                cnt += Math.abs(findLeftCompareCnt) + Math.abs(findRightCompareCnt);
                break;
            } else {
                if (peakOnLeft) {
                    e = mid - 1;
                } else {
                    s =  mid + 1;
                }
            }
        }
        return find ? cnt : -cnt;
    }

    private static int binarySearch(int[] nums, int s, int e, int tar, boolean ascending) {
        int cnt = 1;
        while (s <= e) {
            int mid = s + (e - s) / 2;
            if (tar == nums[mid]) return cnt;
            if ((tar < nums[mid]) == ascending) {
                e = mid - 1;
            } else {
                s = mid + 1;
            }
            cnt++;
        }
        return -cnt;
    }

}
