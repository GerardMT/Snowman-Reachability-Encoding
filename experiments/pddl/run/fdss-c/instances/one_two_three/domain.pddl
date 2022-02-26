    (define (domain snowman_cheating_adl)

    (:requirements
        :typing
        :negative-preconditions
        :equality
        :disjunctive-preconditions
        :conditional-effects
        :action-costs
    )

    (:types
        location direction ball size - object
    )

    (:predicates
        (snow ?l - location)
        (next ?from ?to - location ?dir - direction)
        (occupancy ?l - location)
        (ball_at ?b - ball ?l - location)
        (ball_size_small ?b - ball)
        (ball_size_medium ?b - ball)
        (ball_size_large ?b - ball)
        (goal)
   )

    (:functions
        (total-cost) - number
    )

    (:action move_ball

     :parameters
        (?b - ball ?ppos ?from ?to - location ?dir - direction)

     :precondition
        (and
            (next ?ppos ?from ?dir)
            (next ?from ?to ?dir)
            (ball_at ?b ?from)
            (not (occupancy ?ppos))
            (forall (?o - ball)
                (or
                    (= ?o ?b)
                    (or
                        (not (ball_at ?o ?from))
                        (or
                            (and
                                (ball_size_small ?b)
                                (ball_size_medium ?o))
                            (and
                                (ball_size_small ?b)
                                (ball_size_large ?o))
                            (and
                                (ball_size_medium ?b)
                                (ball_size_large ?o))))))
            (or
                (forall (?o - ball)
                    (or
                        (= ?o ?b)
                        (not (ball_at ?o ?from))))
                (forall (?o - ball)
                        (not (ball_at ?o ?to))))
            (forall (?o - ball)
                    (or
                        (not (ball_at ?o ?to))
                        (or
                            (and
                                (ball_size_small ?b)
                                (ball_size_medium ?o))
                            (and
                                (ball_size_small ?b)
                                (ball_size_large ?o))
                            (and
                                (ball_size_medium ?b)
                                (ball_size_large ?o))))))
     :effect
        (and
            (occupancy ?to)
            (not (ball_at ?b ?from))
            (ball_at ?b ?to)
            (when
                (forall (?o - ball)
                    (or
                        (= ?o ?b)
                        (not (ball_at ?o ?from))))
                (and
                    (not (occupancy ?from))))
            (not (snow ?to))
            (when
                (and
                    (snow ?to)
                    (ball_size_small ?b))
                (and
                    (not (ball_size_small ?b))
                    (ball_size_medium ?b)))
            (when
                (and
                    (snow ?to)
                    (ball_size_medium ?b))
                (and
                    (not (ball_size_medium ?b))
                    (ball_size_large ?b)))
            (increase (total-cost) 1))
    )

    (:action goal

     :parameters
        (?b0 ?b1 ?b2 - ball ?p0 - location ?b3 ?b4 ?b5 - ball ?p1 - location ?b6 ?b7 ?b8 - ball ?p2 - location)

     :precondition
        (and
            (not (= ?b0 ?b1))
            (not (= ?b0 ?b2))
            (not (= ?b0 ?b3))
            (not (= ?b0 ?b4))
            (not (= ?b0 ?b5))
            (not (= ?b0 ?b6))
            (not (= ?b0 ?b7))
            (not (= ?b0 ?b8))
            (not (= ?b1 ?b2))
            (not (= ?b1 ?b3))
            (not (= ?b1 ?b4))
            (not (= ?b1 ?b5))
            (not (= ?b1 ?b6))
            (not (= ?b1 ?b7))
            (not (= ?b1 ?b8))
            (not (= ?b2 ?b3))
            (not (= ?b2 ?b4))
            (not (= ?b2 ?b5))
            (not (= ?b2 ?b6))
            (not (= ?b2 ?b7))
            (not (= ?b2 ?b8))
            (not (= ?b3 ?b4))
            (not (= ?b3 ?b5))
            (not (= ?b3 ?b6))
            (not (= ?b3 ?b7))
            (not (= ?b3 ?b8))
            (not (= ?b4 ?b5))
            (not (= ?b4 ?b6))
            (not (= ?b4 ?b7))
            (not (= ?b4 ?b8))
            (not (= ?b5 ?b6))
            (not (= ?b5 ?b7))
            (not (= ?b5 ?b8))
            (not (= ?b6 ?b7))
            (not (= ?b6 ?b8))
            (not (= ?b7 ?b8))
            (ball_at ?b0 ?p0)
            (ball_at ?b1 ?p0)
            (ball_at ?b2 ?p0)
            (ball_at ?b3 ?p1)
            (ball_at ?b4 ?p1)
            (ball_at ?b5 ?p1)
            (ball_at ?b6 ?p2)
            (ball_at ?b7 ?p2)
            (ball_at ?b8 ?p2)
        )

     :effect
         (and (goal))
    )
)