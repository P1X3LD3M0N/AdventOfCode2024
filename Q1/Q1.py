import os
import collections

def part_1(list1, list2): # O(nlogn) time complexity, O(1) space complexity
    summ = 0

    list1.sort()
    list2.sort()

    for i, j in zip(list1, list2):
        summ += abs(int(i) - int(j))

    return summ

def part_2(list1, list2): # O(n) time complexity, O(n) space complexity
    summ = 0
    freq = collections.Counter(list2)

    for i in range(len(list1)):
        if list1[i] in freq:
            summ += list1[i] * freq[list1[i]]
    
    return summ

if __name__ == "__main__":
    input_file = open("input.txt", "r")
    list1 = []
    list2 = []

    for line in input_file:
        num1, num2 = line.split()
        list1.append(num1)
        list2.append(num2)
    
    ans = part_1(list1, list2)
    print(f"Part 1: {ans}")

    ans = part_2(list1, list2)
    print(f"Part 2: {ans}")
