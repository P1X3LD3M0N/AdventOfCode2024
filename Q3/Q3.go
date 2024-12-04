package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func findAllIndices(s, pattern string) []int {
	var indices []int
	index := 0
	for {
		i := strings.Index(s[index:], pattern)
		if i == -1 {
			break
		}
		position := index + i
		indices = append(indices, position)
		index = position + len(pattern)
	}
	return indices
}

func find_mul(memory string) int {
	total := 0
	digit1 := ""
	digit2 := ""
	char := 0

	for char < len(memory) {
		index := strings.Index(memory[char:], "mul(")
		if index == -1 {
			break
		}
		char += index + 4
		if char >= len(memory) {
			break
		}

		for char < len(memory) && memory[char] >= '0' && memory[char] <= '9' {
			digit1 += string(memory[char])
			char++
			if char >= len(memory) {
				break
			}
		}
		if char >= len(memory) {
			break
		}

		if digit1 != "" && memory[char] == ',' {
			char++
			if char >= len(memory) {
				break
			}
			for char < len(memory) && memory[char] >= '0' && memory[char] <= '9' {
				digit2 += string(memory[char])
				char++
				if char >= len(memory) {
					break
				}
			}
			if char >= len(memory) {
				break
			}
		}

		if digit2 != "" && memory[char] == ')' {
			num1, _ := strconv.Atoi(digit1)
			num2, _ := strconv.Atoi(digit2)
			total += num1 * num2
		}

		digit1 = ""
		digit2 = ""
		char++
	}

	return total
}

func part1(memory string) int {
	return find_mul(memory)
}

func part2(memory string) int {
	total := 0
	enabled := true
	currentIndex := 0
	i := 0

	for i < len(memory) {
		if i+len("don't()") <= len(memory) && memory[i:i+len("don't()")] == "don't()" {
			if enabled {
				substring := memory[currentIndex:i]
				total += find_mul(substring)
			}
			enabled = false
			i += len("don't()")
			currentIndex = i
		} else if i+len("do()") <= len(memory) && memory[i:i+len("do()")] == "do()" {
			if !enabled {
				currentIndex = i + len("do()")
			}
			enabled = true
			i += len("do()")
		} else {
			i++
		}
	}

	if enabled && currentIndex < len(memory) {
		substring := memory[currentIndex:]
		total += find_mul(substring)
	}

	return total
}

func main() {
	file, err := os.Open("input.txt")
	if err != nil {
		fmt.Println(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var content string
	for scanner.Scan() {
		content += scanner.Text()
	}

	fmt.Println("Part 1: ", part1(content))
	fmt.Println("Part 2: ", part2(content))
}
