(define (problem kate_garry_craig)

    (:domain snowman_basic_adl)

    (:objects
        right - direction
        left - direction
        up - direction
        down - direction
        ball_0 - ball
        ball_1 - ball
        ball_2 - ball
        ball_3 - ball
        ball_4 - ball
        ball_5 - ball
        ball_6 - ball
        ball_7 - ball
        ball_8 - ball
        loc_1_2 - location
        loc_1_3 - location
        loc_1_4 - location
        loc_2_2 - location
        loc_2_3 - location
        loc_2_4 - location
        loc_3_1 - location
        loc_3_2 - location
        loc_3_3 - location
        loc_3_4 - location
        loc_4_1 - location
        loc_4_2 - location
        loc_4_3 - location
        loc_4_4 - location
        loc_5_1 - location
        loc_5_2 - location
        loc_5_3 - location
        loc_5_4 - location
        loc_6_2 - location
        loc_6_3 - location
        loc_6_4 - location
        loc_7_2 - location
        loc_7_3 - location
        loc_7_4 - location
    )

    (:init
        (= (total-cost) 0)
        (next loc_1_2 loc_2_2 right)
        (next loc_1_2 loc_1_3 up)
        (next loc_1_3 loc_2_3 right)
        (next loc_1_3 loc_1_4 up)
        (next loc_1_3 loc_1_2 down)
        (next loc_1_4 loc_2_4 right)
        (next loc_1_4 loc_1_3 down)
        (next loc_2_2 loc_3_2 right)
        (next loc_2_2 loc_1_2 left)
        (next loc_2_2 loc_2_3 up)
        (next loc_2_3 loc_3_3 right)
        (next loc_2_3 loc_1_3 left)
        (next loc_2_3 loc_2_4 up)
        (next loc_2_3 loc_2_2 down)
        (next loc_2_4 loc_3_4 right)
        (next loc_2_4 loc_1_4 left)
        (next loc_2_4 loc_2_3 down)
        (next loc_3_1 loc_4_1 right)
        (next loc_3_1 loc_3_2 up)
        (next loc_3_2 loc_4_2 right)
        (next loc_3_2 loc_2_2 left)
        (next loc_3_2 loc_3_3 up)
        (next loc_3_2 loc_3_1 down)
        (next loc_3_3 loc_4_3 right)
        (next loc_3_3 loc_2_3 left)
        (next loc_3_3 loc_3_4 up)
        (next loc_3_3 loc_3_2 down)
        (next loc_3_4 loc_4_4 right)
        (next loc_3_4 loc_2_4 left)
        (next loc_3_4 loc_3_3 down)
        (next loc_4_1 loc_5_1 right)
        (next loc_4_1 loc_3_1 left)
        (next loc_4_1 loc_4_2 up)
        (next loc_4_2 loc_5_2 right)
        (next loc_4_2 loc_3_2 left)
        (next loc_4_2 loc_4_3 up)
        (next loc_4_2 loc_4_1 down)
        (next loc_4_3 loc_5_3 right)
        (next loc_4_3 loc_3_3 left)
        (next loc_4_3 loc_4_4 up)
        (next loc_4_3 loc_4_2 down)
        (next loc_4_4 loc_5_4 right)
        (next loc_4_4 loc_3_4 left)
        (next loc_4_4 loc_4_3 down)
        (next loc_5_1 loc_4_1 left)
        (next loc_5_1 loc_5_2 up)
        (next loc_5_2 loc_6_2 right)
        (next loc_5_2 loc_4_2 left)
        (next loc_5_2 loc_5_3 up)
        (next loc_5_2 loc_5_1 down)
        (next loc_5_3 loc_6_3 right)
        (next loc_5_3 loc_4_3 left)
        (next loc_5_3 loc_5_4 up)
        (next loc_5_3 loc_5_2 down)
        (next loc_5_4 loc_6_4 right)
        (next loc_5_4 loc_4_4 left)
        (next loc_5_4 loc_5_3 down)
        (next loc_6_2 loc_7_2 right)
        (next loc_6_2 loc_5_2 left)
        (next loc_6_2 loc_6_3 up)
        (next loc_6_3 loc_7_3 right)
        (next loc_6_3 loc_5_3 left)
        (next loc_6_3 loc_6_4 up)
        (next loc_6_3 loc_6_2 down)
        (next loc_6_4 loc_7_4 right)
        (next loc_6_4 loc_5_4 left)
        (next loc_6_4 loc_6_3 down)
        (next loc_7_2 loc_6_2 left)
        (next loc_7_2 loc_7_3 up)
        (next loc_7_3 loc_6_3 left)
        (next loc_7_3 loc_7_4 up)
        (next loc_7_3 loc_7_2 down)
        (next loc_7_4 loc_6_4 left)
        (next loc_7_4 loc_7_3 down)
        (character_at loc_4_1)
        (ball_at ball_0 loc_2_3)
        (ball_size_large ball_0)
        (ball_at ball_1 loc_2_3)
        (ball_size_small ball_1)
        (ball_at ball_2 loc_3_3)
        (ball_size_small ball_2)
        (ball_at ball_3 loc_4_2)
        (ball_size_large ball_3)
        (ball_at ball_4 loc_4_2)
        (ball_size_small ball_4)
        (ball_at ball_5 loc_4_3)
        (ball_size_small ball_5)
        (ball_at ball_6 loc_5_3)
        (ball_size_small ball_6)
        (ball_at ball_7 loc_6_3)
        (ball_size_large ball_7)
        (ball_at ball_8 loc_6_3)
        (ball_size_small ball_8)
        (snow loc_1_2)
        (snow loc_1_3)
        (snow loc_1_4)
        (snow loc_2_2)
        (snow loc_2_4)
        (snow loc_3_1)
        (snow loc_3_2)
        (snow loc_3_4)
        (snow loc_4_1)
        (snow loc_4_4)
        (snow loc_5_1)
        (snow loc_5_2)
        (snow loc_5_4)
        (snow loc_6_2)
        (snow loc_6_4)
        (snow loc_7_2)
        (snow loc_7_3)
        (snow loc_7_4)
        (occupancy loc_2_3)
        (occupancy loc_3_3)
        (occupancy loc_4_2)
        (occupancy loc_4_3)
        (occupancy loc_5_3)
        (occupancy loc_6_3)
    )

    (:goal
        (and (goal))
    )

    (:metric minimize (total-cost))
)