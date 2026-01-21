export class ProductFilterModel {

    constructor(
        public id?: number,
        public name?: string,
        public priceCents?: number,
        public category?: string,
        public active?: boolean,
        public stock?: number
    ) {}
}