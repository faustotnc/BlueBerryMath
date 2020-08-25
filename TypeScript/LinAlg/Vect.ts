import { VectorCoords } from './VectorCoords';
import { Matrix, make2dArray } from './Matrix';

export class Vect {
    public vector: number[];
    /** A seed for the random vector generation. */
    public static seed: number;

    /**
     * Creates an n-dimensional vector.
     * @param elements The elements of the vector.
     */
    constructor(...elements: number[]) { this.vector = elements; }


    /**
     * Prints out the vector to the console.
     */
    public print(): void {
        console.log(this.vector);
    }


    /**
     * Calculates the magnitude of the vector
     * @return The magnitude of the vector
     */
    public magnitude(): number {
        let mag = 0;
        for (let element of this.vector) mag += Math.pow(element, 2);
        return Math.sqrt(mag);
    }


    /**
     * Calculates the angle of a 2-dimensional vector
     * @return The angle of a 2-dimensional vector
     */
    public angle(): number {
        return (this.dim() === 2) ? Math.atan2(this.vector[1], this.vector[0]) : NaN;
    }


    /**
     * Calculates the dimensions of the vector
     * @return The dimensions of the vector
     */
    public dim(): number {
        return this.vector.length;
    }


    /**
     * Checks that the passed vector matched the dimension of the current vector.
     * If the dimensions do not match, throws an error.
     * @param v The secondary vector to check.
     * @return True if the dimensions match.
     * @throws Error if the dimensions do not match.
     */
    private dimsMatch(v: Vect): boolean {
        if (this.dim() != v.dim()) {
            throw new Error("The dimensions of the vectors do not match. dim(" + this.dim() + ") != " + "dim(" + v.dim() + ").");
        } else {
            return true;
        }
    }


    /**
     * Obtains the coordinates of the vector (for 2d and 3d vectors)
     * in different coordinate systems.
     */
    public Coords = new VectorCoords(this);


    /**
     * Adds the elements of the vectors to form a new vector.
     * @param v The secondary vector to add to the current vector.
     * @return A new Vect with the sum of the added vectors.
     */
    public plus(v: Vect): Vect {
        let w = new Array(this.dim());
        if (this.dimsMatch(v)) for (let i = 0; i < this.dim(); i++) w[i] = this.getElement(i) + v.vector[i];
        return new Vect(...w);
    }


    /**
     * Subtracts the elements of the vectors to form a new vector.
     * @param v The secondary vector to subtract from the current vector.
     * @return A new Vect with the subtraction of the vectors.
     */
    public minus(v: Vect): Vect {
        let w = new Array(this.dim());
        if (this.dimsMatch(v)) for (let i = 0; i < this.dim(); i++) w[i] = this.getElement(i) - v.vector[i];
        return new Vect(...w);
    }


    /**
     * Scales the vector by alpha.
     * @param alpha The quantity by which to scale the vector.
     * @return A new Vect as the scaled version of this vector.
     */
    public scale(alpha: number): Vect {
        let w = new Array(this.dim());
        for (let i = 0; i < this.dim(); i++) w[i] = this.getElement(i) * alpha;
        return new Vect(...w);
    }


    /**
     * Generates a new Vect with the normalized form of the this vector.
     * @return A new Vect with the normalized form of the this vector.
     */
    public norm(): Vect {
        let vMag = this.magnitude();
        let vNorm = new Array(this.dim());;
        for (let i = 0; i < this.dim(); i++) vNorm[i] = this.getElement(i) / vMag;
        return new Vect(...vNorm);
    }


    /**
     * Calculates the dot-product of two vectors.
     * @param v The secondary vector to calculate the dot-product.
     * @return The dot product of the two vectors.
     * @throws Error if the dimensions of the two vectors do not match.
     */
    public dot(v: Vect): number {
        let prod = 0;
        if (this.dimsMatch(v)) {
            for (let i = 0; i < this.dim(); i++) {
                let el = this.getElement(i);
                prod += el * v.vector[i];
            }
        }
        return prod;
    }


    /**
     * Calculates the cross-product of two vectors in R^3.
     * @param v The secondary vector in R^3 to calculate the cross-product.
     * @return A new Vect in R^3 as the result of the cross-product of the two vectors.
     */
    public cross(v: Vect): Vect {
        if (this.dim() != 3 || v.dim() != 3) {
            throw new Error("Vectors must form a subset of R^3.");
        } else {
            let e1 = this.vector[1] * v.vector[2] - v.vector[1] * this.vector[2];
            let e2 = this.vector[2] * v.vector[0] - v.vector[2] * this.vector[0];
            let e3 = this.vector[0] * v.vector[1] - v.vector[0] * this.vector[1];
            return new Vect(...[e1, e2, e3]);
        }
    }


    /**
     * Obtains the element of the vector at position pos.
     * @param pos The position of the element to obtain.
     * @return The element of this vector at position pos.
     */
    public getElement(pos: number): number { return this.vector[pos]; }


    /**
     * Sets the element of the vector at position <code>pos</code> to the value <code>newEl</code>.
     * @param pos The position where to set the new element.
     * @param newEl The new element to set at position <code>pos</code>.
     */
    public setElement(pos: number, newEl: number): void { this.vector[pos] = newEl; }


    /**
     * Converts the vector into a column vector (a matrix with one column and n rows).
     * @return A new Matrix with one column and n rows.
     */
    public toColumn(): Matrix {
        let c: number[][] = make2dArray(this.dim(), 1);
        for (let i = 0; i < this.dim(); i++) c[i][0] = this.getElement(i);
        return new Matrix(c);
    }


    /**
     * Converts the vector into a row vector (a matrix with one row and n columns).
     * @return A new Matrix with one row and n columns.
     */
    public toRow(): Matrix {
        let c = make2dArray(1, this.dim());
        for (let i = 0; i < this.dim(); i++) c[0][i] = this.getElement(i);
        return new Matrix(c);
    }


    /**
     * Maps each element of this vector to a new Vect based on the provided mapping function f.
     * @param f The mapping function to be applied to each element of this vector.
     * @return A new Vect whole elements are the mapped elements of this vector based on the mapping function f.
     */
    public map(f: (x: number) => number): Vect {
        let V = new Array(this.dim());
        for (let i = 0; i < this.dim(); i++) V[i] = f(this.getElement(i));
        return new Vect(...V);
    }


    /**
     * Generates a vector of dimension <code>dim</code> with random elements between zero and one.
     * @param dim The dimension of the vector.
     * @return A new Vect whose elements are random doubles between zero and one.
     */
    public static rand(dim: number) {
        // let random = (this.seed && this.seed !== 0) ? new Random(seed) : new Random();
        let vectEls = new Array(dim);

        for (let i = 0; i < dim; i++) vectEls[i] = Math.random();
        return new Vect(...vectEls);
    }


    /**
     * Generates a zero-vector of dimension <code>dim</code>.
     * @param dim The dimension of the zero vector.
     * @return A new Vect of dimension <code>dim</code> whose elements are all zeros.
     */
    public static zeros(dim: number): Vect {
        let vectEls = new Array(dim);
        for (let i = 0; i < dim; i++) vectEls[i] = 0;
        return new Vect(...vectEls);
    }


    /**
     * Generates a one-hot vector.
     * A one-hot vector is a vector whose elements are all zeros,
     * except for one element with value of 1 at position x.
     * @param dim The dimension of the vector.
     * @param hotPos The position of the 1 in the one-hot vector.
     * @return A new Vect whose elements are all zeros, except for the
     * element at position <code>pos</code> with value of 1.
     */
    public static oneHot(dim: number, hotPos: number): Vect {
        let vectEls = new Array(dim);
        for (let i = 0; i < dim; i++) vectEls[i] = (i === hotPos) ? 1 : 0;
        return new Vect(...vectEls);
    }
}
