import math
import tkinter as tk


class Hexagon:
    def __init__(self, parent, x, y, length, color, tag):
        self.parent = parent
        self.x = x
        self.y = y
        self.length = length
        self.color = color
        self.tag = tag
        self.draw_hex()

    def draw_hex(self):
        coords = []
        angle = 60
        start_x = self.x
        start_y = self.y

        for i in range(6):
            coords.append((start_x, start_y))
            start_x += self.length * math.cos(math.radians(angle * i))
            start_y += self.length * math.sin(math.radians(angle * i))

        flat_coords = [c for point in coords for c in point]

        self.parent.create_polygon(
            flat_coords,
            fill=self.color,
            outline="black",
            tags=self.tag
        )


class FrequencyReuse(tk.Tk):
    CANVAS_WIDTH = 800
    CANVAS_HEIGHT = 650

    def __init__(self, i_val, j_val, cluster_size, cols=16, rows=10, edge_len=30):
        super().__init__()

        self.i = i_val
        self.j = j_val
        self.cluster_size = cluster_size
        self.edge_len = edge_len

        self.hexagons = []
        self.reuse_list = []
        self.co_cell_centers = []
        self.first_click = True
        self.curr_angle = 330
        self.curr_count = 0

        self.title("Frequency Reuse – Co-channel Cells")

        self.canvas = tk.Canvas(
            self,
            width=self.CANVAS_WIDTH,
            height=self.CANVAS_HEIGHT,
            bg="#4dd0e1"
        )
        self.canvas.pack()

        self.canvas.bind("<Button-1>", self.call_back)
        self.bind("<Shift-R>", self.reset_grid)

        self.create_grid(cols, rows)
        self.create_textbox()
        self.calculate_geometry()

    def create_grid(self, cols, rows):
        size = self.edge_len

        for c in range(cols):
            offset = size * math.sqrt(3) / 2 if c % 2 else 0
            for r in range(rows):
                x = c * (size * 1.5) + 50
                y = r * (size * math.sqrt(3)) + offset + 30
                tag = f"{r},{c}"
                hexagon = Hexagon(self.canvas, x, y, size, "#fafafa", tag)
                self.hexagons.append(hexagon)

    def calculate_geometry(self):
        self.hex_radius = math.sqrt(3) / 2 * self.edge_len
        self.center_dist = math.sqrt(3) * self.hex_radius

    def create_textbox(self):
        self.textbox = tk.Label(
            self,
            text="Select a hexagon",
            font=("Helvetica", 12),
            bg="#4dd0e1"
        )
        self.textbox.pack(pady=5)

    def write_text(self, msg):
        self.textbox.config(text=msg)

    def reset_grid(self, event=None):
        self.first_click = True
        self.curr_angle = 330
        self.curr_count = 0
        self.reuse_list.clear()
        self.co_cell_centers.clear()

        for hx in self.hexagons:
            self.canvas.itemconfig(hx.tag, fill="#fafafa")

        self.canvas.delete("line")
        self.write_text("Select a hexagon")

    def call_back(self, event):
        item = self.canvas.find_closest(event.x, event.y)[0]
        tag = self.canvas.gettags(item)[0]

        hexagon = next(h for h in self.hexagons if h.tag == tag)
        center = (hexagon.x + 15, hexagon.y + 25)

        if self.first_click:
            self.first_click = False
            self.canvas.itemconfig(hexagon.tag, fill="green")
            self.co_cell_centers.append(center)
            self.write_text("Select the co-channel cells")

            for _ in range(6):
                end_x = center[0] + self.center_dist * self.i * math.cos(math.radians(self.curr_angle))
                end_y = center[1] + self.center_dist * self.i * math.sin(math.radians(self.curr_angle))

                reuse_x = end_x + self.center_dist * self.j * math.cos(math.radians(self.curr_angle - 60))
                reuse_y = end_y + self.center_dist * self.j * math.sin(math.radians(self.curr_angle - 60))

                target = (reuse_x, reuse_y) if self.j != 0 else (end_x, end_y)
                found = self.canvas.find_closest(target[0], target[1])[0]
                self.reuse_list.append(found)

                self.co_cell_centers.append((end_x, end_y))
                self.curr_angle -= 60

        else:
            if item in self.reuse_list:
                self.canvas.itemconfig(hexagon.tag, fill="green")
                self.write_text(f"Correct! Cell {hexagon.tag} is a co-cell.")
                self.curr_count += 1

                if self.curr_count == len(self.reuse_list):
                    self.draw_lines()
                    self.write_text("Great! Press Shift + R to restart")
            else:
                self.canvas.itemconfig(hexagon.tag, fill="red")
                self.write_text(f"Incorrect! Cell {hexagon.tag} is not a co-cell.")

    def draw_lines(self):
        cx, cy = self.co_cell_centers[0]
        for x, y in self.co_cell_centers[1:]:
            self.canvas.create_line(cx, cy, x, y, width=2, tags="line")


if __name__ == "__main__":
    print("Common (i, j) values: (1,0), (1,1), (2,0), (2,1), (3,0), (2,2)")
    i = int(input("Enter i: "))
    j = int(input("Enter j: "))

    if i == 0 and j == 0:
        raise ValueError("i and j cannot both be zero")
    if j > i:
        raise ValueError("j cannot be greater than i")

    N = i**2 + i*j + j**2
    print(f"Cluster size N = {N}")

    app = FrequencyReuse(i, j, N)
    app.mainloop()
