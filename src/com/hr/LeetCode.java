package com.hr;

import java.util.*;

public class LeetCode {
    public static void main(String[] args){
//        LeetCode leetCode = new LeetCode();
//        System.out.println(leetCode.mySqrt(9));
        System.out.println(0b101^0b11);
    }

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     * */
    public int[] twoSum(int[] nums, int target) {
//        // 1
//        List<Integer> dealNums = new ArrayList<>();
//        int firstIndex;
//        for(int lastIndex=0;lastIndex<nums.length;lastIndex++){
//            if((firstIndex = dealNums.indexOf(target - nums[lastIndex])) != -1){
//                return new int[]{firstIndex,lastIndex};
//            }else{
//                dealNums.add(nums[lastIndex]);
//            }
//        }
//        return null;

        // 2 因为题目中说“假设每种输入只会对应一个答案”，所以可以用Map(值一样的肯定不是答案)
        HashMap<Integer,Integer> dealNums = new HashMap<>();
        Integer firstIndex;
        for(int lastIndex=0;lastIndex<nums.length;lastIndex++){
            if((firstIndex = dealNums.get(target - nums[lastIndex])) != null){
                return new int[]{firstIndex,lastIndex};
            }else{
                dealNums.put(target - nums[lastIndex],lastIndex);
            }
        }
        return null;
    }

    /**
     * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
     *
     * 示例 1:
     * 输入: 123
     * 输出: 321
     *
     * 示例 2:
     * 输入: -123
     * 输出: -321
     *
     * 示例 3:
     * 输入: 120
     * 输出: 21
     *
     *  注意:
     * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−2的31次方,  2的31次方 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
     */
    public int reverse(int x) {
//        // 1
//        String xStr = String.valueOf(x);
//        boolean isMinus = false;
//        if(xStr.startsWith("-")){
//            isMinus = true;
//            xStr = xStr.substring(1);
//        }
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = xStr.length()-1; i >= 0; i--) {
//            stringBuilder.append(xStr.charAt(i));
//        }
//        long reverseX = Long.valueOf(stringBuilder.toString());
//        if(isMinus){
//            return reverseX > 2147483648L ? 0 : (int)-reverseX;
//        }else{
//            return reverseX > 2147483648L - 1 ? 0 : (int)reverseX;
//        }

        // 2 因为题目中说“假设我们的环境只能存储得下 32 位的有符号整数”，所以不能定义long型变量
        String xStr = String.valueOf(x);
        boolean isMinus = false;
        if(xStr.startsWith("-")){
            isMinus = true;
            xStr = xStr.substring(1);
        }
        boolean isMaxLength = xStr.length() == 10;
        String max = "2147483648";
        StringBuilder stringBuilder = new StringBuilder();
        boolean isOk = false;
        for (int i = xStr.length()-1; i >= 0; i--) {
            char c = xStr.charAt(i);
            if(isMaxLength && !isOk) {
                if (c > max.charAt(xStr.length() - 1 - i)) {
                    return 0;
                }else if(c < max.charAt(xStr.length() - 1 - i)){
                    isOk = true;
                }else if(!isMinus && i == 0){
                    return 0;
                }
            }
            stringBuilder.append(c);
        }
        return isMinus ? -Integer.valueOf(stringBuilder.toString()) : Integer.valueOf(stringBuilder.toString());
    }

    /**
     * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     *
     * 示例 1:
     * 输入: 121
     * 输出: true
     *
     * 示例 2:
     * 输入: -121
     * 输出: false
     * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
     *
     * 示例 3:
     * 输入: 10
     * 输出: false
     * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
     */
    public boolean isPalindrome(int x) {
        if(x < 0){
            return false;
        }
        if(x < 10){
            return true;
        }
        if(x%10 == 0){
            return false;
        }
        int halfX = 0;
        while (halfX*10 <= x){
            halfX = halfX*10 + x%10;
            x /= 10;
        }
        if(halfX == x){
            return true;
        }else if(halfX > x){
            return halfX/10 == x;
        }
        return false;
    }

    /**
     * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
     *
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     *
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     *
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
     *
     * 示例 1:
     * 输入: "III"
     * 输出: 3
     *
     * 示例 2:
     * 输入: "IV"
     * 输出: 4
     *
     * 示例 3:
     * 输入: "IX"
     * 输出: 9
     *
     * 示例 4:
     * 输入: "LVIII"
     * 输出: 58
     * 解释: L = 50, V= 5, III = 3.
     *
     * 示例 5:
     * 输入: "MCMXCIV"
     * 输出: 1994
     * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
     */
    public int romanToInt(String s) {
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            num = romanToIntHelpFun(num, s.charAt(i));
        }
        return num;
    }

    private int romanToIntHelpFun(int origin, char c){
        switch (c){
            case 'I':
                return origin + 1;
            case 'V':
                return origin%10 == 1 ? origin + 3 : origin + 5;
            case 'X':
                return origin + 10;
            case 'L':
                return origin%100 == 10 ? origin + 30 : origin + 50;
            case 'C':
                return origin + 100;
            case 'D':
                return origin%1000 == 100 ? origin + 300 : origin + 500;
            case 'M':
                return origin + 1000;
        }
        return origin;
    }

    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     *
     * 如果不存在公共前缀，返回空字符串 ""。
     *
     * 示例 1:
     * 输入: ["flower","flow","flight"]
     * 输出: "fl"
     *
     * 示例 2:
     * 输入: ["dog","racecar","car"]
     * 输出: ""
     * 解释: 输入不存在公共前缀。
     *
     * 说明:
     * 所有输入只包含小写字母 a-z 
     */
    public String longestCommonPrefix(String[] strs) {
        int minLength = strs[0].length();
        for (String str : strs) {
            minLength = Math.min(minLength,str.length());
        }
        return longestCommonPrefixHelpFun(strs,0,minLength);
    }

    private String longestCommonPrefixHelpFun(String[] strs, int start, int end) {
        String pre = strs[0].substring(start,end);
        for (int i = 1; i < strs.length; i++) {
            if(!pre.equals(strs[i].substring(start,end))){
                if(end-start == 1){
                    return "";
                }
                String longestCommonPrefix = longestCommonPrefixHelpFun(strs, start, start + (end - start) / 2);
                if(longestCommonPrefix.length() == start + (end - start) / 2 - start){
                    return longestCommonPrefix + longestCommonPrefixHelpFun(strs,start + (end-start)/2, end);
                }
                return longestCommonPrefix;
            }
        }
        return pre;
    }

    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     *
     * 有效字符串需满足：
     *
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     *
     * 示例 1:
     * 输入: "()"
     * 输出: true
     *
     * 示例 2:
     * 输入: "()[]{}"
     * 输出: true
     *
     * 示例 3:
     * 输入: "(]"
     * 输出: false
     */
    public boolean isValid(String s) {
        // 1
        if(s.length()%2 == 1){
            return false;
        }
        HashMap<Character,Character> hashMap = new HashMap<>();
        hashMap.put('(',')');
        hashMap.put('[',']');
        hashMap.put('{','}');
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            try {
                Character peek = stack.peek();
                Character preCharacter = hashMap.get(peek);
                if(preCharacter == null || hashMap.containsKey(c)){
                    stack.push(c);
                } else if(preCharacter.equals(c)){
                    stack.pop();
                }else {
                    return false;
                }
            }catch (EmptyStackException e){
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    /**
     * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
     *
     * 示例：
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     */

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null){
            return l2;
        }else if(l2 == null){
            return l1;
        }else if(l1.val < l2.val){
            l1.next = mergeTwoLists(l1.next,l2);
            return l1;
        }else{
            l2.next = mergeTwoLists(l1,l2.next);
            return l2;
        }
    }

    /**
     * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
     *
     * 示例 1:
     * 给定数组 nums = [1,1,2],
     * 函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。
     * 你不需要考虑数组中超出新长度后面的元素。
     *
     * 示例 2:
     * 给定 nums = [0,0,1,1,1,2,2,3,3,4],
     * 函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
     * 你不需要考虑数组中超出新长度后面的元素。
     *
     * 说明:
     * 为什么返回数值是整数，但输出的答案是数组呢?
     * 请注意，输入数组是以“引用”方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
     *
     * 你可以想象内部操作如下:
     * // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
     * int len = removeDuplicates(nums);
     * // 在函数里修改输入数组对于调用者是可见的。
     * // 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
     * for (int i = 0; i < len; i++) {
     *     print(nums[i]);
     * }
     */
    public int removeDuplicates(int[] nums) {
//        int length = nums.length;
//        if(length == 0){
//            return 0;
//        }
//        int flag = nums[0];
//        for (int i = 0,j = 0; i < length-1; i++) {
//            if(nums[i-j] == nums[i+1]){
//                nums[i+1] = flag;
//                j++;
//            }else{
//                j = 0;
//            }
//        }
//        int removeCount = 0;
//        for (int i = 1; i < length; i++) {
//            if(nums[i] == flag){
//                removeCount++;
//            }else{
//                nums[i-removeCount] = nums[i];
//            }
//        }
//        return length - removeCount;

        // 2
        int length = nums.length;
        if(length == 0){
            return 0;
        }
        int j = 0;
        for (int i = 0; i < length-1; i++) {
            if(nums[j] != nums[i+1]){
                j++;
                nums[j] = nums[i+1];
            }
        }
        return j+1;
    }

    /**
     * 给定一个数组 nums 和一个值 val，你需要原地移除所有数值等于 val 的元素，返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     *
     * 示例 1:
     * 给定 nums = [3,2,2,3], val = 3,
     * 函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
     * 你不需要考虑数组中超出新长度后面的元素。
     *
     * 示例 2:
     * 给定 nums = [0,1,2,2,3,0,4,2], val = 2,
     * 函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。
     * 注意这五个元素可为任意顺序。
     * 你不需要考虑数组中超出新长度后面的元素。
     */

    public int removeElement(int[] nums, int val) {
        int length = nums.length;
        if(length == 0){
            return 0;
        }
        int removeCount = 0;
        for (int i = 0; i < length; i++) {
            if(nums[i] == val){
                removeCount++;
            }else{
                nums[i-removeCount] = nums[i];
            }
        }
        return length - removeCount;
    }

    /**
     * 实现 strStr() 函数。
     * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
     *
     * 示例 1:
     * 输入: haystack = "hello", needle = "ll"
     * 输出: 2
     *
     * 示例 2:
     * 输入: haystack = "aaaaa", needle = "bba"
     * 输出: -1
     *
     * 说明:
     * 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
     * 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与C语言的 strstr() 以及 Java的 indexOf() 定义相符。
     */
    public int strStr(String haystack, String needle) {
        if(haystack == null || needle == null){
            return -1;
        }
        int needleLength = needle.length();
        if(needleLength == 0){
            return 0;
        }
        int i = 0,j;
        while(i <= haystack.length() - needleLength){
            if(strStrHelpFun(haystack.charAt(i+needleLength-1),needle)){
                for (j = i; j < i+needleLength; j++) {
                    if(haystack.charAt(j) != needle.charAt(j-i)){
                        break;
                    }
                }
                if(j == i+needleLength){
                    return i;
                }
                i++;
            }else{
                i += needleLength;
            }
        }
        return -1;
    }

    public boolean strStrHelpFun(char c, String needle){
        for (int i = 0; i < needle.length(); i++) {
            if(needle.charAt(i) == c){
                return true;
            }
        }
        return false;
    }

    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 你可以假设数组中无重复元素。
     *
     * 示例 1:
     * 输入: [1,3,5,6], 5
     * 输出: 2
     *
     * 示例 2:
     * 输入: [1,3,5,6], 2
     * 输出: 1
     *
     * 示例 3:
     * 输入: [1,3,5,6], 7
     * 输出: 4
     *
     * 示例 4:
     * 输入: [1,3,5,6], 0
     * 输出: 0
     */
    public int searchInsert(int[] nums, int target) {
        return searchInsertHelpFun(nums, 0, nums.length, target);
    }

    private int searchInsertHelpFun(int[] nums, int start, int end, int target) {
        int targetIndex = start + (end-start)/2;
        if(target < nums[targetIndex]){
            if(start == targetIndex){
                return start;
            }
            return searchInsertHelpFun(nums,start,targetIndex,target);
        }else if(target > nums[targetIndex]){
            if(end == targetIndex+1){
                return end;
            }
            return searchInsertHelpFun(nums,targetIndex+1,end,target);
        }else{
            return targetIndex;
        }
    }

    /**
     * 报数序列是一个整数序列，按照其中的整数的顺序进行报数，得到下一个数。其前五项如下：
     * 1.     1 <1,1>
     * 2.     11 <2,1>
     * 3.     21 <1,2><1,1>
     * 4.     1211 <1,1><1,2><2,1>
     * 5.     111221 <2,1><1,1><1,2><1,2><1,1>  ->  相邻的value相等的合并<3,1><2,2><1,1>
     * 1 被读作  "one 1"  ("一个一") , 即 11。
     * 11 被读作 "two 1s" ("两个一"）, 即 21。
     * 21 被读作 "one 2",  "one 1" （"一个二" ,  "一个一") , 即 1211。
     *
     * 给定一个正整数 n（1 ≤ n ≤ 30），输出报数序列的第 n 项。
     * 注意：整数顺序将表示为一个字符串。
     *
     * 示例 1:
     * 输入: 1
     * 输出: "1"
     *
     * 示例 2:
     * 输入: 4
     * 输出: "1211"
     */
    public String countAndSay(int n) {
        if(n == 1){
            return "1";
        }
        List<CountAndSayHelpClass> countAndSayHelpClassList = new ArrayList<>();
        List<CountAndSayHelpClass> tempCountAndSayHelpClassList = new ArrayList<>();
        countAndSayHelpClassList.add(new CountAndSayHelpClass(1,1));
        for (int i = 2; i < n; i++) {
            for (CountAndSayHelpClass countAndSayHelpClass : countAndSayHelpClassList) {
                if(countAndSayHelpClass.key == countAndSayHelpClass.value){
                    tempCountAndSayHelpClassList.add(new CountAndSayHelpClass(2,countAndSayHelpClass.value));
                }else{
                    tempCountAndSayHelpClassList.add(new CountAndSayHelpClass(1,countAndSayHelpClass.key));
                    tempCountAndSayHelpClassList.add(new CountAndSayHelpClass(1,countAndSayHelpClass.value));
                }
            }
            // 这里可以优化去掉，在添加的时候就合并
            for (int i1 = 0; i1 < tempCountAndSayHelpClassList.size() -1; i1++) {
                if(tempCountAndSayHelpClassList.get(i1).value == tempCountAndSayHelpClassList.get(i1+1).value){
                    tempCountAndSayHelpClassList.get(i1+1).key += tempCountAndSayHelpClassList.get(i1).key;
                    tempCountAndSayHelpClassList.remove(i1);
                }
            }
            countAndSayHelpClassList = tempCountAndSayHelpClassList;
            tempCountAndSayHelpClassList = new ArrayList<>();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (CountAndSayHelpClass countAndSayHelpClass : countAndSayHelpClassList) {
            stringBuilder.append(countAndSayHelpClass);
        }
        return stringBuilder.toString();
    }

    class CountAndSayHelpClass{
        int key;
        int value;

        CountAndSayHelpClass(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return ""+key+value;
        }
    }

    /**
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     *
     * 示例:
     * 输入: [-2,1,-3,4,-1,2,1,-5,4],
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     *
     * 进阶:
     * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
     */
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if(sum > max){
                max = sum;
            }
            if(sum < 0){
                sum = 0;
            }
        }
        return max;
    }

    /**
     * 给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
     * 如果不存在最后一个单词，请返回 0 。
     * 说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。
     *
     * 示例:
     * 输入: "Hello World"
     * 输出: 5
     */
    public int lengthOfLastWord(String s) {
//        String[] words = s.split(" ");
//        if(words.length > 0){
//            return words[words.length-1].length();
//        }else{
//            return 0;
//        }
        int len = 0;
        for (int i = s.length()-1; i >= 0; i--) {
            if(s.charAt(i) != ' '){
                len++;
            }else if(len > 0){
                return len;
            }
        }
        return len;
    }

    /**
     * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     *
     * 示例 1:
     * 输入: [1,2,3]
     * 输出: [1,2,4]
     * 解释: 输入数组表示数字 123。
     *
     * 示例 2:
     * 输入: [4,3,2,1]
     * 输出: [4,3,2,2]
     * 解释: 输入数组表示数字 4321。
     */
    public int[] plusOne(int[] digits) {
        int i = digits.length-1;
        while (i >= 0) {
            if(digits[i]+1 == 10){
                digits[i] = 0;
                i--;
            }else{
                digits[i] += 1;
                return digits;
            }
        }
        int[] newDigits = new int[digits.length+1];
        newDigits[0] = 1;
        return newDigits;
    }

    /**
     * 给你两个二进制字符串，返回它们的和（用二进制表示）。
     * 输入为 非空 字符串且只包含数字 1 和 0。
     *
     * 示例 1:
     * 输入: a = "11", b = "1"
     * 输出: "100"
     *
     * 示例 2:
     * 输入: a = "1010", b = "1011"
     * 输出: "10101"
     *  
     *
     * 提示：
     * 每个字符串仅由字符 '0' 或 '1' 组成。
     * 1 <= a.length, b.length <= 10^4
     * 字符串如果不是 "0" ，就都不含前导零。
     */
    public String addBinary(String a, String b) {
        char[] aArr,bArr;
        if(a.length() > b.length()){
            aArr = a.toCharArray();
            bArr = b.toCharArray();
        }else{
            bArr = a.toCharArray();
            aArr = b.toCharArray();
        }
        boolean plusOne = false;
        for (int i = 0; i < aArr.length; i++) {
            char aChar = aArr[aArr.length-1-i];
            char bChar;
            if(i < bArr.length){
                bChar = bArr[bArr.length-1-i];
            }else if(plusOne){
                bChar = '1';
                plusOne = false;
            }else{
                break;
            }
            if(((bChar == '0') && plusOne) || ((bChar == '1') && !plusOne)){
                aArr[aArr.length-1-i] = aChar == '0' ? '1' : '0';
            }
            if((plusOne && aChar == '0' && bChar == '0') || (!plusOne && aChar == '1' && bChar == '1')){
                plusOne = !plusOne;
            }
        }
        if(plusOne){
            return "1"+new String(aArr);
        }else{
            return new String(aArr);
        }
    }

    /**
     * 实现 int sqrt(int x) 函数。
     * 计算并返回 x 的平方根，其中 x 是非负整数。
     * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
     *
     * 示例 1:
     * 输入: 4
     * 输出: 2
     *
     * 示例 2:
     * 输入: 8
     * 输出: 2
     * 说明: 8 的平方根是 2.82842...,
     *      由于返回类型是整数，小数部分将被舍去。
     */
    public int mySqrt(int x) {
//        思考更优解
        if(x < 2){
            return x;
        }
        return mySqrtHelpFun(x/2, (int)Math.ceil(x/4f), x);
    }

    private int mySqrtHelpFun(int value, int gap, int x){
//        System.out.println(value+"-"+gap);
        float divide = x/value;
        if(divide == value){
            return value;
        } else if(divide > value){
            if(gap == 1 && (value+1)>x/(value+1)){
                return value;
            }
            return mySqrtHelpFun(value+gap, (int)Math.ceil(gap/2f), x);
        }else {
            return mySqrtHelpFun(value-gap, (int)Math.ceil(gap/2f), x);
        }
    }

    /**
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 注意：给定 n 是一个正整数。
     *
     * 示例 1：
     * 输入： 2
     * 输出： 2
     * 解释： 有两种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶
     * 2.  2 阶
     *
     * 示例 2：
     * 输入： 3
     * 输出： 3
     * 解释： 有三种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶 + 1 阶
     * 2.  1 阶 + 2 阶
     * 3.  2 阶 + 1 阶
     */
    public int climbStairs(int n) {
//        时间太长，因为有太多重复计算
//        if(n < 3){
//            return n;
//        }
//        return climbStairs(n-1)+climbStairs(n-2);
        int pre1 = 1;
        int pre2 = 1;
        int total = 1;
        for (int i = 1; i < n; i++) {
            total = pre1+pre2;
            pre1 = pre2;
            pre2 = total;
        }
        return total;
    }

    /**
     * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
     *
     * 示例 1:
     * 输入: 1->1->2
     * 输出: 1->2
     *
     * 示例 2:
     * 输入: 1->1->2->3->3
     * 输出: 1->2->3
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode first = head;
        while (head != null && head.next != null){
            if(head.next.val == head.val){
                head.next = head.next.next;
            }else{
                head = head.next;
            }
        }
        return first;
    }

    /**
     * 给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
     *
     * 说明:
     * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
     * 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
     *  
     *
     * 示例:
     * 输入:
     * nums1 = [1,2,3,0,0,0], m = 3
     * nums2 = [2,5,6],       n = 3
     * 输出: [1,2,2,3,5,6]
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int point1 = m-1,point2 = n-1;
        for (int i = m+n-1; i >= 0; i--) {
            if(point2 < 0){
                break;
            }
            if(point1 < 0 || nums1[point1] < nums2[point2]){
                nums1[i] = nums2[point2];
                point2--;
            }else {
                nums1[i] = nums1[point1];
                point1--;
            }
        }
    }

    /**
     * 给定两个二叉树，编写一个函数来检验它们是否相同。
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     *
     * 示例 1:
     * 输入:       1         1
     *           / \       / \
     *          2   3     2   3
     *
     *         [1,2,3],   [1,2,3]
     * 输出: true
     *
     * 示例 2:
     * 输入:      1          1
     *           /           \
     *          2             2
     *
     *         [1,2],     [1,null,2]
     *
     * 输出: false
     *
     * 示例 3:
     * 输入:       1         1
     *           / \       / \
     *          2   1     1   2
     *
     *         [1,2,1],   [1,1,2]
     * 输出: false
     */
     // Definition for a binary tree node.
     public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode(int x) { val = x; }
     }

    public boolean isSameTree(TreeNode p, TreeNode q) {
         if((p == null && q != null) || (p != null && q == null)){
             return false;
         }else if(p == null){
             return true;
         }else {
             return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
         }
    }

    /**
     * 给定一个二叉树，检查它是否是镜像对称的。
     * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
     *
     *     1
     *    / \
     *   2   2
     *  / \ / \
     * 3  4 4  3
     *  
     *
     * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
     *
     *     1
     *    / \
     *   2   2
     *    \   \
     *    3    3
     *  
     *
     * 进阶：
     * 你可以运用递归和迭代两种方法解决这个问题吗？
     */
    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        return isSymmetricHelpFun(root.left, root.right);
    }

    private boolean isSymmetricHelpFun(TreeNode left, TreeNode right){
        if(left == null && right == null){
            return true;
        }else if(left == null || right == null || left.val != right.val){
            return false;
        }else {
            return isSymmetricHelpFun(left.right, right.left) && isSymmetricHelpFun(left.left, right.right);
        }
    }

    /**
     * 给定一个二叉树，找出其最大深度。
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     *
     * 说明: 叶子节点是指没有子节点的节点。
     *
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最大深度 3 。
     */
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }else if(root.left == null && root.right == null){
            return 1;
        }else{
            return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
        }
    }

    /**
     * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
     *
     * 例如：
     * 给定二叉树 [3,9,20,null,null,15,7],
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回其自底向上的层次遍历为：
     *
     * [
     *   [15,7],
     *   [9,20],
     *   [3]
     * */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        levelOrderBottomHelpFun(result, 0, root);
        return result;
    }

    private void levelOrderBottomHelpFun(List<List<Integer>> result, int index, TreeNode node){
        if(node == null){
            return;
        }
        if(index >= result.size()){
            result.add(0, new ArrayList<>());
        }
        result.get(result.size()-1-index).add(node.val);
        levelOrderBottomHelpFun(result, index+1, node.left);
        levelOrderBottomHelpFun(result, index+1, node.right);
    }

    /**
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
     *
     * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
     *
     * 示例:
     * 给定有序数组: [-10,-3,0,5,9],
     * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
     *
     *       0
     *      / \
     *    -3   9
     *    /   /
     *  -10  5
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if(nums.length == 0){
            return null;
        }
        TreeNode treeNode = new TreeNode(nums[nums.length/2]);
        int leftLength = nums.length/2;
        if(leftLength > 0){
            int[] left = new int[leftLength];
            for (int i = 0; i < leftLength; i++) {
                left[i] = nums[i];
            }
            treeNode.left = sortedArrayToBST(left);
            int rightLength = nums.length - leftLength - 1;
            if(rightLength > 0){
                int[] right= new int[rightLength];
                for (int i = leftLength+1; i < nums.length; i++) {
                    right[i-leftLength-1] = nums[i];
                }
                treeNode.right = sortedArrayToBST(right);
            }
        }
        return treeNode;
    }

    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * 本题中，一棵高度平衡二叉树定义为：
     * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
     *
     * 示例 1:
     * 给定二叉树 [3,9,20,null,null,15,7]
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回 true
     *
     * 示例 2:
     * 给定二叉树 [1,2,2,3,3,null,null,4,4]
     *
     *        1
     *       / \
     *      2   2
     *     / \
     *    3   3
     *   / \
     *  4   4
     * 返回 false
     */
    public boolean isBalanced(TreeNode root) {
        // 有点扯淡，明明说了是个二叉树，一个null算毛线的二叉树呀
        if(root == null){
            return true;
        }
        int leftDeep = isBalancedHelpFun(root.left, 0);
        int rightDeep = isBalancedHelpFun(root.right, 0);
        return leftDeep >= 0 && rightDeep >= 0 && Math.abs(leftDeep - rightDeep) <= 1;
    }

    private int isBalancedHelpFun(TreeNode root, int deep){
        if(root == null){
            return deep;
        }
        int leftDeep = isBalancedHelpFun(root.left, deep + 1);
        int rightDeep = isBalancedHelpFun(root.right, deep + 1);
        if(leftDeep >= 0 && rightDeep >= 0 && Math.abs(leftDeep - rightDeep) <= 1){
            return Math.max(leftDeep, rightDeep);
        }
        return -1;
    }

    /**
     * 给定一个二叉树，找出其最小深度。
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     * 说明: 叶子节点是指没有子节点的节点。
     *
     * 示例:
     * 给定二叉树 [3,9,20,null,null,15,7],
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最小深度  2.
     */
    public int minDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            return 1;
        }else if(root.left == null || root.right == null){
            return 1+Math.max(minDepth(root.left), minDepth(root.right));
        }
        return 1+Math.min(minDepth(root.left), minDepth(root.right));
    }

    /**
     * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
     * 说明: 叶子节点是指没有子节点的节点。
     *
     * 示例: 
     * 给定如下二叉树，以及目标和 sum = 22，
     *               5
     *              / \
     *             4   8
     *            /   / \
     *           11  13  4
     *          /  \      \
     *         7    2      1
     * 返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        // 真扯淡，上面null算二叉树，这里又不算了
        if(root == null){
            return false;
        }
        if(root.left == null && root.right == null){
            return sum == root.val;
        }
        if(root.left != null){
            if(hasPathSum(root.left, sum - root.val)){
                return true;
            }
        }
        return hasPathSum(root.right, sum - root.val);
    }

    /**
     * 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
     *
     * 示例:
     * 输入: 6
     * 输出:
     * [
     *      [1],
     *     [1,1],
     *    [1,2,1],
     *   [1,3,3,1],
     *  [1,4,6,4,1],
     * [1,5,10,10,5,1]
     * ]
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> listList = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < i + 1; j++) {
                int left = 0,right = 0;
                if(i == 0){
                    left = 1;
                } else if(j > 0){
                    left = listList.get(i-1).get(j-1);
                }
                if(j < i){
                    right = listList.get(i-1).get(j);
                }
                list.add(left+right);
            }
            listList.add(list);
        }
        return listList;
    }

    /**
     * 给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
     *
     * 示例:
     * 输入: 3
     * 输出: [1,3,3,1]
     */
    public List<Integer> getRow(int rowIndex) {
        List<Integer> list = new ArrayList<>();
        return list;
    }

    /**
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
     * 注意：你不能在买入股票前卖出股票。
     *
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     *
     * 示例 2:
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     */
    public int maxProfit(int[] prices) {
        int max = 0;
        if(prices.length > 1){
            int buy = prices[0];
            for (int i = 1; i < prices.length; i++) {
                if(prices[i] < buy){
                    buy = prices[i];
                }else{
                    max = Math.max(max, prices[i] - buy);
                }
            }
        }
        return max;
    }

    /**
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     *
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
     *
     * 示例 2:
     * 输入: [1,2,3,4,5]
     * 输出: 4
     * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
     *      因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
     *
     * 示例 3:
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     *  
     *
     * 提示：
     * 1 <= prices.length <= 3 * 10 ^ 4
     * 0 <= prices[i] <= 10 ^ 4
     */
    public int maxProfit2(int[] prices) {
        int profit = 0;
        int curProfile = 0;
        if(prices.length > 1) {
            int buy = prices[0];
            for (int i = 1; i < prices.length; i++) {
                int sellProfile = prices[i] - buy;
                if(sellProfile <= curProfile){
                    profit += curProfile;
                    curProfile = 0;
                    buy = prices[i];
                }else{
                    curProfile = sellProfile;
                }
            }
        }
        return profit+curProfile;
    }

    /**
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     * 说明：本题中，我们将空字符串定义为有效的回文串。
     *
     * 示例 1:
     * 输入: "A man, a plan, a canal: Panama"
     * 输出: true
     *
     * 示例 2:
     * 输入: "race a car"
     * 输出: false
     */
    public boolean isPalindrome(String s) {
        int start = 0,end = s.length()-1;
        while(end-start > 0){
            char startC = s.charAt(start);
            if(!Character.isLetterOrDigit(startC)){
                start++;
                continue;
            }
            char endC = s.charAt(end);
            if(!Character.isLetterOrDigit(endC)){
                end--;
                continue;
            }
            if(Character.toLowerCase(startC) != Character.toLowerCase(endC)){
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    /**
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 说明：
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     *
     * 示例 1:
     * 输入: [2,2,1]
     * 输出: 1
     *
     * 示例 2:
     * 输入: [4,1,2,1,2]
     * 输出: 4
     */
    public int singleNumber(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            nums[0] ^= nums[i];
        }
        return nums[0];
    }

    /**
     * 给定一个链表，判断链表中是否有环。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     * 如果链表中存在环，则返回 true 。 否则，返回 false 。
     *
     * 进阶：
     * 你能用 O(1)（即，常量）内存解决此问题吗？
     *
     * 示例 1：
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：true
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     *
     * 示例 2：
     * 输入：head = [1,2], pos = 0
     * 输出：true
     * 解释：链表中有一个环，其尾部连接到第一个节点。
     *
     * 示例 3：
     * 输入：head = [1], pos = -1
     * 输出：false
     * 解释：链表中没有环。
     *  
     *
     * 提示：
     * 链表中节点的数目范围是 [0, 104]
     * -105 <= Node.val <= 105
     * pos 为 -1 或者链表中的一个 有效索引 。
     */
    public boolean hasCycle(ListNode head) {
        while (head != null){
            if(head.val == 106){
                return true;
            }else{
                head.val = 106;
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
     * push(x) —— 将元素 x 推入栈中。
     * pop() —— 删除栈顶的元素。
     * top() —— 获取栈顶元素。
     * getMin() —— 检索栈中的最小元素。
     *  
     * 示例:
     * 输入：
     * ["MinStack","push","push","push","getMin","pop","top","getMin"]
     * [[],[-2],[0],[-3],[],[],[],[]]
     * 输出：
     * [null,null,null,null,-3,null,0,-2]
     *
     * 解释：
     * MinStack minStack = new MinStack();
     * minStack.push(-2);
     * minStack.push(0);
     * minStack.push(-3);
     * minStack.getMin();   --> 返回 -3.
     * minStack.pop();
     * minStack.top();      --> 返回 0.
     * minStack.getMin();   --> 返回 -2.
     *  
     * 提示：
     * pop、top 和 getMin 操作总是在 非空栈 上调用。
     */
    /**
     * 解法1：这种方式有点大材小用了，并不需要排序。每次入栈或出栈只需要记住当前栈的最小值就可以了
     */
    class MinStack {
        Stack<ListNode> stack = new Stack<>();
        ListNode minListNode = null;
        public MinStack() {

        }

        public void push(int x) {
            ListNode listNode = new ListNode(x);
            if(minListNode == null){
                minListNode = listNode;
            }else{
                ListNode preListNode = null;
                ListNode nextListNode = minListNode;
                while(nextListNode != null){
                    if(nextListNode.val >= listNode.val){
                        listNode.next = nextListNode;
                        if(preListNode == null){
                            minListNode = listNode;
                        }else{
                            preListNode.next = listNode;
                        }
                        break;
                    }
                    preListNode = nextListNode;
                    nextListNode = nextListNode.next;
                }
            }
            stack.push(listNode);
        }

        public void pop() {
            ListNode pop = stack.pop();
            if(minListNode.val == pop.val && minListNode.next == pop.next){
                minListNode = minListNode.next;
            }else{
                ListNode preListNode = minListNode;
                ListNode nextListNode= minListNode.next;
                while(nextListNode != null){
                    if(nextListNode.val == pop.val && nextListNode.next == pop.next){
                        preListNode.next = nextListNode.next;
                        break;
                    }
                    preListNode = nextListNode;
                    nextListNode = nextListNode.next;
                }
            }
        }

        public int top() {
            return stack.peek().val;
        }

        public int getMin() {
            return minListNode.val;
        }
    }
    /**
     * 解法2
     */
//    class MinStack {
//        class Pair{
//            int first;
//            int second;
//            Pair(int f, int s){
//                first = f;
//                second = s;
//            }
//        }
//        Stack<Pair> stack = new Stack<>();
//        public MinStack() {
//
//        }
//
//        public void push(int x) {
//            int min = x;
//            if(!stack.isEmpty()){
//                min = stack.peek().second;
//            }
//            stack.push(new Pair(x, Math.min(x, min)));
//        }
//
//        public void pop() {
//            stack.pop();
//        }
//
//        public int top() {
//            return stack.peek().first;
//        }
//
//        public int getMin() {
//            return stack.peek().second;
//        }
//    }


}
