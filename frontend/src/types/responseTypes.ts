
import { ApprovalStatus, BilkentCourse, CourseRequest, CourseWishlist, ExistingCourseWishlist, HostCourse, LearningAgreement, PreApprovalForm, PreviousCourseRequest, Student, StudentAssociatedCourse, StudentAssociatedUniPastEvaluationItem, StudentAssociatedWishlist, UniAssociatedCourse, UniversityDetailed, UniversityProxy, User } from ".";

export interface Response<T> {
    data: T,
    message: string,
}

export type ResponseUser = Response<User>

// Wishlist
export type ResponseStudentCourseWishlist = Response<ExistingCourseWishlist>
export type ResponseAllStudentCourseWishlist = Response<Array<ExistingCourseWishlist>>
export type ResponseStudentSpecificCourseWishlist = Response<StudentAssociatedWishlist>
export type ResponseCoordinatorAllStudentCourseWishlist = Response<Array<ExistingCourseWishlist>>

// Preapproval form
export type ResponseAllSubmitedPreApprovals = Response<Array<PreApprovalForm>>
export type ResponseApprovePreApprovalStatus = Response<ApprovalStatus>
export type ResponsePreApprovalForm = Response<PreApprovalForm>
export type ResponsePreApprovalFormList = Response<Array<PreApprovalForm>>

// Host & Bilkent courses
export type ResponseBilkentCourseList = Response<Array<BilkentCourse>>
export type ResponseBilkentCourse = Response<BilkentCourse>
export type ResponseHostUniCourses = Response<Array<HostCourse>>


export type ResponsePreviousCourseRequests = Response<Array<CourseRequest>>

export type ResponseCourseRequest = Response<CourseRequest>


export type ResponseUniversities = Response<Array<UniversityProxy>>

export type ResponseUniversity = Response<UniversityDetailed>

export type ResponseStudentPastEvaluation = Response<StudentAssociatedUniPastEvaluationItem>

export type ResponseAllLearningAgreements = Response<Array<LearningAgreement>>

export type ResponseStudent = Response<Student>
