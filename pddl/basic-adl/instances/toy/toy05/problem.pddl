(define (problem toy05)

    (:domain snowman_basic_adl_snowmen_1)

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
        loc_1_4 - location
        loc_2_1 - location
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
    )

    (:init
        (= (total-cost) 0)
        (next loc_1_1 loc_2_1 right)
        (next loc_1_1 loc_1_2 up)
        (next loc_1_2 loc_2_2 right)
        (next loc_1_2 loc_1_3 up)
        (next loc_1_2 loc_1_1 down)
        (next loc_1_3 loc_2_3 right)
        (next loc_1_3 loc_1_4 up)
        (next loc_1_3 loc_1_2 down)
        (next loc_1_4 loc_2_4 right)
        (next loc_1_4 loc_1_3 down)
        (next loc_2_1 loc_3_1 right)
        (next loc_2_1 loc_1_1 left)
        (next loc_2_1 loc_2_2 up)
        (next loc_2_2 loc_3_2 right)
        (next loc_2_2 loc_1_2 left)
        (next loc_2_2 loc_2_3 up)
        (next loc_2_2 loc_2_1 down)
        (next loc_2_3 loc_3_3 right)
        (next loc_2_3 loc_1_3 left)
        (next loc_2_3 loc_2_4 up)
        (next loc_2_3 loc_2_2 down)
        (next loc_2_4 loc_3_4 right)
        (next loc_2_4 loc_1_4 left)
        (next loc_2_4 loc_2_3 down)
        (next loc_3_1 loc_4_1 right)
        (next loc_3_1 loc_2_1 left)
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
        (next loc_4_1 loc_3_1 left)
        (next loc_4_1 loc_4_2 up)
        (next loc_4_2 loc_3_2 left)
        (next loc_4_2 loc_4_3 up)
        (next loc_4_2 loc_4_1 down)
        (next loc_4_3 loc_3_3 left)
        (next loc_4_3 loc_4_4 up)
        (next loc_4_3 loc_4_2 down)
        (next loc_4_4 loc_3_4 left)
        (next loc_4_4 loc_4_3 down)
        (character_at loc_1_4)
        (ball_at ball_0 loc_2_3)
        (ball_size_small ball_0)
        (ball_at ball_1 loc_2_4)
        (ball_size_small ball_1)
        (ball_at ball_2 loc_4_3)
        (ball_size_small ball_2)
        (snow loc_3_3)
        (snow loc_3_4)
        (snow loc_4_4)
        (occupancy loc_2_3)
        (occupancy loc_2_4)
        (occupancy loc_4_3)
    )

    (:goal
        (and (goal))
    )

    (:metric minimize (total-cost))
)