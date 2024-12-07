require 'set'

def traverse(map)
    dirs = [[-1, 0], [0, 1], [1, 0], [0, -1]]
    curr_dir = 0
    positions = Set.new
    visited_states = Set.new

    cost = 0
    
    for i in 0..map.length-1
        for j in 0..map[0].length-1
            if map[i][j] == '^'
                curr_pos = [i, j]
                break
            end
        end
    end

    while true
        if curr_pos[0] < 0 || curr_pos[0] >= map.length || curr_pos[1] < 0 || curr_pos[1] >= map[0].length
            break
        end

        state = [curr_pos, curr_dir]
        if visited_states.include?(state)
            return -1
        end
        visited_states.add(state)

        if map[curr_pos[0]][curr_pos[1]] == '#'
            curr_pos[0] = curr_pos[0] - dirs[curr_dir][0]
            curr_pos[1] = curr_pos[1] - dirs[curr_dir][1]
            curr_dir = (curr_dir + 1) % 4
        else
            positions.add([curr_pos[0], curr_pos[1]])
            curr_pos[0] = curr_pos[0] + dirs[curr_dir][0]
            curr_pos[1] = curr_pos[1] + dirs[curr_dir][1]
        end
    end

    return positions.length
end

def part1(map)
    return traverse(map)
end

def part2(map)
    new_obstructions = 0

    for i in 0..map.length-1
        for j in 0..map[0].length-1
            if map[i][j] == '.'
                map[i][j] = '#'
                if traverse(map) == -1
                    new_obstructions += 1
                end
                map[i][j] = '.'
            end
        end
    end

    return new_obstructions
end

map = []

File.foreach("input.txt") { |line| map.push(line.chomp.chars) }

puts "Part 1: #{part1(map)}"
puts "Part 2: #{part2(map)}"
