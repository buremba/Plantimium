import pygame
import math
import time


def create_background(width, height):
        colors = [(255, 255, 255), (212, 212, 212)]
        background = pygame.Surface((width, height))
        tile_width = 20
        y = 0
        while y < height:
                x = 0
                while x < width:
                        row = y // tile_width
                        col = x // tile_width
                        pygame.draw.rect(
                                background, 
                                colors[(row + col) % 2],
                                pygame.Rect(x, y, tile_width, tile_width))
                        x += tile_width
                y += tile_width
        return background

def is_trying_to_quit(event):
        pressed_keys = pygame.key.get_pressed()
        alt_pressed = pressed_keys[pygame.K_LALT] or pressed_keys[pygame.K_RALT]
        x_button = event.type == pygame.QUIT
        altF4 = alt_pressed and event.type == pygame.KEYDOWN and event.key == pygame.K_F4
        escape = event.type == pygame.KEYDOWN and event.key == pygame.K_ESCAPE
        return x_button or altF4 or escape

def overalIndex(arr,index):
        if(index==0):
            return 0
        return int(math.fmod(index,len(arr)))
def run_demos(width, height, fps):
        pygame.init()
        screen = pygame.display.set_mode((width, height))
        pygame.display.set_caption('press space to see next demo')
        background = create_background(width, height)

        pl = [(50,50),(200,50),(200,200),(50,200)]
        pl=getSmoother(pl,2)
        clock = pygame.time.Clock()
        while True:
                for event in pygame.event.get():
                        if is_trying_to_quit(event):
                                return
                screen.blit(background, (0, 0)) 
                pygame.draw.polygon(screen, (0, 0, 255), pl)
                pygame.display.flip()
                clock.tick(fps)
def getSmoother(polygon,Iteration):
        pl=polygon
        for i in range(Iteration):
            pl2=[]
            cursor=0
            for i in range(len(pl)):
                i1=overalIndex(pl,i);
                i2=overalIndex(pl,i+1);
                i0=overalIndex(pl,i-1);
                i3=overalIndex(pl,i+2);
                print i1,i2,i0,i3
                cs=getControls(pl[i1],pl[i2],pl[i0],pl[i3])
                pl2.append(pl[overalIndex(pl,i)])
                pl2.append(cs[0])
                pl2.append(cs[1])
                pl2.append(pl[overalIndex(pl,i+1)]) 
            pl=pl2
        return pl
def getControls(p1,p2,prep1,postp2):
        x1=p1[0]
        y1=p1[1]
        x2=p2[0]
        y2=p2[1]
        
        x0=prep1[0]
        y0=prep1[1]
        
        x3=postp2[0]
        y3=postp2[1]
        smooth_value=0.5
        xc1 = (x0 + x1) / 2.0;
        yc1 = (y0 + y1) / 2.0;
        xc2 = (x1 + x2) / 2.0;
        yc2 = (y1 + y2) / 2.0;
        xc3 = (x2 + x3) / 2.0;
        yc3 = (y2 + y3) / 2.0;

        len1 = math.sqrt((x1-x0) * (x1-x0) + (y1-y0) * (y1-y0));
        len2 = math.sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1));
        len3 = math.sqrt((x3-x2) * (x3-x2) + (y3-y2) * (y3-y2));

        k1 = len1 / (len1 + len2);
        k2 = len2 / (len2 + len3);

        xm1 = xc1 + (xc2 - xc1) * k1;
        ym1 = yc1 + (yc2 - yc1) * k1;

        xm2 = xc2 + (xc3 - xc2) * k2;
        ym2 = yc2 + (yc3 - yc2) * k2;
        ctrl1_x = xm1 + (xc2 - xm1) * smooth_value + x1 - xm1;
        ctrl1_y = ym1 + (yc2 - ym1) * smooth_value + y1 - ym1;

        ctrl2_x = xm2 + (xc2 - xm2) * smooth_value + x2 - xm2;
        ctrl2_y = ym2 + (yc2 - ym2) * smooth_value + y2 - ym2;    
        return [(ctrl1_x,ctrl1_y),(ctrl2_x,ctrl2_y)]
run_demos(400, 300, 60)