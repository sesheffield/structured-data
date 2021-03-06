(ns structured-data)

(defn do-a-thing [x]
  (let [xx (+ x x)]
    (Math/pow xx xx)))
  

(defn spiff [v]
  (+ (get v 0) (get v 2)))

(defn cutify [v]
  (conj v "<3"))

(defn spiff-destructuring [v]
  (let [[x y z] v]
    (+ x z)))

(defn point [x y]
  [x y])

(defn rectangle [bottom-left top-right]
  [bottom-left top-right])

(defn width [rectangle]
  (let [[[x1 y1] [x2 y2]] rectangle]
    (- x2 x1)))

(defn height [rectangle]
  (let [[[x1 y1] [x2 y2]] rectangle]
    (- y2 y1)))

(defn square? [rectangle]
  (let [[[x1 y1] [x2 y2]] rectangle]
    (if (and (= (- x1 x2) (- y1 y2)))
             true
             false)))

(defn area [rectangle]
  (let [[[x1 y1] [x2 y2]] rectangle]
    (* (- x2 x1) (- y2 y1))))

(defn contains-point? [rectangle point]
  (let [[[x1 y1] [x2 y2]] rectangle]
    (let [[z1 z2] point]
    (if (and (<= x1 z1 x2) (<= y1 z2 y2))
             true
             false))))

(defn contains-rectangle? [outer inner]
  (let [[bottom-left top-right] inner]
    (and (contains-point? outer bottom-left) (contains-point? outer top-right))))

(defn title-length [book]
  (count (:title book))) 

(defn author-count [book]
  (count (:authors book)))

(defn multiple-authors? [book]
  (if (> (count (:authors book)) 1)
    true
    false))

(defn add-author [book new-author]
 (let [lst (:authors book)]
   (conj lst new-author)))

(defn alive? [author]
  (if (contains? author :death-year)
    false
    true))

(defn element-lengths [collection]
  (map count collection))

(defn second-elements [collection]
  (let [sec (fn [x] (second x))]
    (map sec collection)))

(defn titles [books]
  (map :title books))

(defn monotonic? [a-seq]
  (if (or (apply <= a-seq) (apply >= a-seq))
    true
    false))

(defn stars [n]
  (apply str(repeat n "*")))

(defn toggle [a-set elem]
  (if (contains? a-set elem)
    (disj a-set elem)
    (conj a-set elem)))

(defn contains-duplicates? [a-seq]
  (if (= (count(set a-seq))(count a-seq))
    false 
    true))

(defn old-book->new-book [book]
  (assoc book :authors (set (:authors book))))


(defn has-author? [book author]
    (contains? (:authors book) author))

(defn authors [books]
    (apply clojure.set/union (map :authors (map old-book->new-book books))))

(defn all-author-names [books]
    (set (map :name (authors books))))

(defn author->string [author]
    (let [ life (fn [x]
                      (cond
                             (:death-year x) (str " (" (:birth-year x) " - " (:death-year x) ")")
                             (:birth-year x) (str " (" (:birth-year x) " - )")
                             :else "")) ]
        (str (:name author) (life author))))

(defn authors->string [authors]
    (apply str (interpose ", " (map author->string authors))))

(defn book->string [book]
    (str (:title book) ", written by " (authors->string (:authors book))))

(defn books->string [books]
    (let [ x (count books)  ]
         (cond
               (== x 0) "No books."
               (== x 1) (str "1 book. " (book->string (first (seq books)))".")
               :else (str x " books. " (apply str (interpose ". " (map book->string books)))"."))))

(defn books-by-author [author books]
    (filter (fn [book] (has-author? book author)) books))

(defn author-by-name [name authors]
  (first (filter (fn [x] (= (:name x) name)) authors))) 

(defn living-authors [authors]
  (filter alive? authors))

(defn has-a-living-author? [book]
  (not (empty? (living-authors (:authors book)))))

(defn books-by-living-authors [books]
  (filter has-a-living-author? books))

; %________%
