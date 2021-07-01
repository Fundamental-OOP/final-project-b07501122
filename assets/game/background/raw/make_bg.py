import cv2
import numpy as np


# opencv: HWC
bg = np.zeros((312, 432, 3))
def add_bg_patch(i, j, patch):
    h, w, _ = patch.shape
    bg[i:i+h, j:j+w, :] = patch

# sky_blue
sky_blue = cv2.imread('sky_blue.png')
for i in range(12):
    for j in range(432 // 16):
        add_bg_patch(16*i, 16*j, sky_blue)

# mountain
mountain = cv2.imread('mountain.png')
add_bg_patch(188, 0, mountain)

# ground_red
ground_red = cv2.imread('ground_red.png')
for i in range(432 // 16):
    add_bg_patch(248, 16*i, ground_red)

# ground line
ground_line = cv2.imread('ground_line.png')
ground_line_leftmost = cv2.imread('ground_line_leftmost.png')
ground_line_righttmost = cv2.imread('ground_line_rightmost.png')
for i in range(1, 432 // 16 - 1):
    add_bg_patch(264, 16*i, ground_line)
add_bg_patch(264, 0, ground_line_leftmost)
add_bg_patch(264, 432-16, ground_line_righttmost)

# ground_yellow
ground_yellow = cv2.imread('ground_yellow.png')
for i in range(2):
    for j in range(432 // 16):
        add_bg_patch(280+16*i, 16*j, ground_yellow)

# net pillar
net_pillar = cv2.imread('net_pillar.png')
net_pillar_top = cv2.imread('net_pillar_top.png')
add_bg_patch(176, 213, net_pillar_top)
for i in range(12):
    add_bg_patch(184+8*i, 213, net_pillar)

# save
cv2.imwrite('../background.png', bg)