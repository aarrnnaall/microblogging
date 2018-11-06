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
    republishedBy?: IPublication;
    favedBies?: IPublisher[];
    likedBies?: IPublisher[];
    taggedBies?: ITag[];
    publisher?: IPublisher;
    mentions?: IPublisher[];
}

export class Publication implements IPublication {
    constructor(
        public id?: number,
        public date?: Moment,
        public content?: string,
        public visible?: boolean,
        public country?: string,
        public city?: string,
        public republishedBy?: IPublication,
        public favedBies?: IPublisher[],
        public likedBies?: IPublisher[],
        public taggedBies?: ITag[],
        public publisher?: IPublisher,
        public mentions?: IPublisher[]
    ) {
        this.visible = this.visible || false;
    }
}
