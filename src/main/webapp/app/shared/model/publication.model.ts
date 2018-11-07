import { Moment } from 'moment';
import { IPublication } from 'app/shared/model//publication.model';
import { IPublisher } from 'app/shared/model//publisher.model';
import { ITag } from 'app/shared/model//tag.model';

export interface IPublication {
    id?: number;
    date?: Moment;
    content?: string;
    visible?: boolean;
    country?: string;
    city?: string;
    republish?: IPublication;
    mentions?: IPublisher[];
    favedBies?: IPublisher[];
    likedBies?: IPublisher[];
    tags?: ITag[];
    publisher?: IPublisher;
}

export class Publication implements IPublication {
    constructor(
        public id?: number,
        public date?: Moment,
        public content?: string,
        public visible?: boolean,
        public country?: string,
        public city?: string,
        public republish?: IPublication,
        public mentions?: IPublisher[],
        public favedBies?: IPublisher[],
        public likedBies?: IPublisher[],
        public tags?: ITag[],
        public publisher?: IPublisher
    ) {
        this.visible = this.visible || false;
    }
}
