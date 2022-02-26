(define (problem claire)

    (:domain snowman_basic_adl)

    (:objects
        right - direction
        left - direction
        up - direction
        down - direction
        ball_0 - ball
        ball_1 - ball
        ball_2 - ball
        loc_1_1 - location
        loc_1_2 - location
        loc_1_3 - location
        loc_2_1 - location
        loc_2_2 - location
        loc_2_3 - location
        loc_3_2 - location
        loc_4_1 - location
        loc_4_2 - location
        loc_4_3 - location
        loc_5_1 - location
        loc_5_2 - location
        loc_5_3 - location
        loc_6_1 - location
        loc_6_2 - location
        loc_6_3 - location
        loc_7_1 - location
        loc_7_2 - location
        loc_7_3 - location
    )

    (:init
        (= (total-cost) 0)
        (next loc_1_1 loc_2_1 right)
        (next loc_1_1 loc_1_2 up)
        (next loc_1_2 loc_2_2 right)
        (next loc_1_2 loc_1_3 up)
        (next loc_1_2 loc_1_1 down)
        (next loc_1_3 loc_2_3 right)
        (next loc_1_3 loc_1_2 down)
        (next loc_2_1 loc_1_1 left)
        (next loc_2_1 loc_2_2 up)
        (next loc_2_2 loc_3_2 right)
        (next loc_2_2 loc_1_2 left)
        (next loc_2_2 loc_2_3 up)
        (next loc_2_2 loc_2_1 down)
        (next loc_2_3 loc_1_3 left)
        (next loc_2_3 loc_2_2 down)
        (next loc_3_2 loc_4_2 right)
        (next loc_3_2 loc_2_2 left)
        (next loc_4_1 loc_5_1 right)
        (next loc_4_1 loc_4_2 up)
        (next loc_4_2 loc_5_2 right)
        (next loc_4_2 loc_3_2 left)
        (next loc_4_2 loc_4_3 up)
        (next loc_4_2 loc_4_1 down)
        (next loc_4_3 loc_5_3 right)
        (next loc_4_3 loc_4_2 down)
        (next loc_5_1 loc_6_1 right)
        (next loc_5_1 loc_4_1 left)
        (next loc_5_1 loc_5_2 up)
        (next loc_5_2 loc_6_2 right)
        (next loc_5_2 loc_4_2 left)
        (next loc_5_2 loc_5_3 up)
        (next loc_5_2 loc_5_1 down)
        (next loc_5_3 loc_6_3 right)
        (next loc_5_3 loc_4_3 left)
        (next loc_5_3 loc_5_2 down)
        (next loc_6_1 loc_7_1 right)
        (next loc_6_1 loc_5_1 left)
        (next loc_6_1 loc_6_2 up)
        (next loc_6_2 loc_7_2 right)
        (next loc_6_2 loc_5_2 left)
        (next loc_6_2 loc_6_3 up)
        (next loc_6_2 loc_6_1 down)
        (next loc_6_3 loc_7_3 right)
        (next loc_6_3 loc_5_3 left)
        (next loc_6_3 loc_6_2 down)
        (next loc_7_1 loc_6_1 left)
        (next loc_7_1 loc_7_2 up)
        (next loc_7_2 loc_6_2 left)
        (next loc_7_2 loc_7_3 up)
        (next loc_7_2 loc_7_1 down)
        (next loc_7_3 loc_6_3 left)
        (next loc_7_3 loc_7_2 down)
        (character_at loc_1_2)
        (ball_at ball_0 loc_3_2)
        (ball_size_small ball_0)
        (ball_at ball_1 loc_5_2)
        (ball_size_medium ball_1)
        (ball_at ball_2 loc_6_2)
        (ball_size_medium ball_2)
        (snow loc_7_1)
        (snow loc_7_2)
        (snow loc_7_3)
        (occupancy loc_3_2)
        (occupancy loc_5_2)
        (occupancy loc_6_2)
    )

    (:goal
        (and (goal))
    )

    (:metric minimize (total-cost))
)