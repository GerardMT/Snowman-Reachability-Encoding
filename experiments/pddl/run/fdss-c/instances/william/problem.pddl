(define (problem william)

    (:domain snowman_cheating_adl)

    (:objects
        right - direction
        left - direction
        up - direction
        down - direction
        ball_0 - ball
        ball_1 - ball
        ball_2 - ball
        loc_1_2 - location
        loc_1_3 - location
        loc_1_4 - location
        loc_2_2 - location
        loc_2_3 - location
        loc_2_4 - location
        loc_3_2 - location
        loc_3_4 - location
        loc_4_1 - location
        loc_4_2 - location
        loc_4_3 - location
        loc_4_4 - location
        loc_4_5 - location
        loc_5_1 - location
        loc_5_2 - location
        loc_5_4 - location
        loc_5_5 - location
        loc_6_1 - location
        loc_6_2 - location
        loc_6_3 - location
        loc_6_4 - location
        loc_6_5 - location
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
        (next loc_2_3 loc_1_3 left)
        (next loc_2_3 loc_2_4 up)
        (next loc_2_3 loc_2_2 down)
        (next loc_2_4 loc_3_4 right)
        (next loc_2_4 loc_1_4 left)
        (next loc_2_4 loc_2_3 down)
        (next loc_3_2 loc_4_2 right)
        (next loc_3_2 loc_2_2 left)
        (next loc_3_4 loc_4_4 right)
        (next loc_3_4 loc_2_4 left)
        (next loc_4_1 loc_5_1 right)
        (next loc_4_1 loc_4_2 up)
        (next loc_4_2 loc_5_2 right)
        (next loc_4_2 loc_3_2 left)
        (next loc_4_2 loc_4_3 up)
        (next loc_4_2 loc_4_1 down)
        (next loc_4_3 loc_4_4 up)
        (next loc_4_3 loc_4_2 down)
        (next loc_4_4 loc_5_4 right)
        (next loc_4_4 loc_3_4 left)
        (next loc_4_4 loc_4_5 up)
        (next loc_4_4 loc_4_3 down)
        (next loc_4_5 loc_5_5 right)
        (next loc_4_5 loc_4_4 down)
        (next loc_5_1 loc_6_1 right)
        (next loc_5_1 loc_4_1 left)
        (next loc_5_1 loc_5_2 up)
        (next loc_5_2 loc_6_2 right)
        (next loc_5_2 loc_4_2 left)
        (next loc_5_2 loc_5_1 down)
        (next loc_5_4 loc_6_4 right)
        (next loc_5_4 loc_4_4 left)
        (next loc_5_4 loc_5_5 up)
        (next loc_5_5 loc_6_5 right)
        (next loc_5_5 loc_4_5 left)
        (next loc_5_5 loc_5_4 down)
        (next loc_6_1 loc_5_1 left)
        (next loc_6_1 loc_6_2 up)
        (next loc_6_2 loc_5_2 left)
        (next loc_6_2 loc_6_3 up)
        (next loc_6_2 loc_6_1 down)
        (next loc_6_3 loc_6_4 up)
        (next loc_6_3 loc_6_2 down)
        (next loc_6_4 loc_5_4 left)
        (next loc_6_4 loc_6_5 up)
        (next loc_6_4 loc_6_3 down)
        (next loc_6_5 loc_5_5 left)
        (next loc_6_5 loc_6_4 down)
        (character_at loc_5_1)
        (ball_at ball_0 loc_2_3)
        (ball_size_small ball_0)
        (ball_at ball_1 loc_4_3)
        (ball_size_large ball_1)
        (ball_at ball_2 loc_6_3)
        (ball_size_medium ball_2)
        (snow loc_1_2)
        (snow loc_1_3)
        (snow loc_1_4)
        (snow loc_2_2)
        (snow loc_2_4)
        (snow loc_3_2)
        (snow loc_3_4)
        (snow loc_4_2)
        (snow loc_4_4)
        (snow loc_5_2)
        (snow loc_5_4)
        (snow loc_6_2)
        (snow loc_6_4)
        (occupancy loc_2_3)
        (occupancy loc_4_3)
        (occupancy loc_6_3)
    )

    (:goal
        (and (goal))
    )

    (:metric minimize (total-cost))
)