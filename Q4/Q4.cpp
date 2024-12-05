#include <iostream>
#include <fstream>
#include <vector>
#include <string>

#include <vector>
#include <string>

int part1(const std::vector<std::vector<char>>& grid) {
    std::string word = "XMAS";
    int rows = grid.size();
    int cols = grid[0].size();
    int word_length = word.length();
    std::vector<std::pair<int, int>> directions = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1}, 
        {-1, -1}, {1, 1}, {-1, 1}, {1, -1}
    };
    int count = 0;

    auto is_valid = [&](int x, int y) {
        return 0 <= x && x < rows && 0 <= y && y < cols;
    };

    auto search_from = [&](int x, int y, std::pair<int, int> d, const std::string& target) {
        int dx = d.first;
        int dy = d.second;
        for (int i = 0; i < target.length(); ++i) {
            int nx = x + i * dx;
            int ny = y + i * dy;
            if (!is_valid(nx, ny) || grid[nx][ny] != target[i]) {
                return false;
            }
        }
        return true;
    };

    for (int x = 0; x < rows; ++x) {
        for (int y = 0; y < cols; ++y) {
            for (const auto& d : directions) {
                if (search_from(x, y, d, word) || 
                    search_from(x, y, d, std::string(word.rbegin(), word.rend()))) {
                    count++;
                }
            }
        }
    }
    return count/2;
}


int part2(const std::vector<std::vector<char>>& grid) {
    int rows = grid.size();
    int cols = grid[0].size();
    int count = 0;

    if (rows < 3 || cols < 3) return 0;

    auto is_xmas = [&](int x, int y) {
        bool diag1 = (grid[x][y] == 'M' && grid[x+1][y+1] == 'A' && grid[x+2][y+2] == 'S') ||
                     (grid[x][y] == 'S' && grid[x+1][y+1] == 'A' && grid[x+2][y+2] == 'M');

        bool diag2 = (grid[x+2][y] == 'M' && grid[x+1][y+1] == 'A' && grid[x][y+2] == 'S') ||
                     (grid[x+2][y] == 'S' && grid[x+1][y+1] == 'A' && grid[x][y+2] == 'M');

        return diag1 && diag2;
    };

    for (int x = 0; x <= rows - 3; ++x) {
        for (int y = 0; y <= cols - 3; ++y) {
            if (is_xmas(x, y)) {
                count++;
            }
        }
    }

    return count;
}


int main() {
    std::ifstream inputFile("input.txt");
    if (!inputFile) {
        std::cerr << "Unable to open file input.txt";
        return 1;
    }

    std::vector<std::vector<char>> grid;
    std::string line;
    while (std::getline(inputFile, line)) {
        std::vector<char> row;
        for (char c : line) {
            if (std::isalpha(c)) {
                row.push_back(c);
            }
        }
        grid.push_back(row);
    }

    inputFile.close();

    std::cout << "Part 1: " << part1(grid) << std::endl;
    std::cout << "Part 2: " << part2(grid) << std::endl;

    return 0;
}
